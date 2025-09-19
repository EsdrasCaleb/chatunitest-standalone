package zju.cst.aces.ant;

import org.junit.platform.launcher.listeners.TestExecutionSummary;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.dto.ClassInfo;
import zju.cst.aces.dto.MethodInfo;
import zju.cst.aces.dto.PromptInfo;
import zju.cst.aces.runner.AbstractRunner;
import zju.cst.aces.runner.ClassRunner;
import zju.cst.aces.util.chattester.TesterValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestRunnerAndReporter {

    private final Config config;
    // ... (constantes como TEST_DIR_PREFIX, etc., continuam as mesmas) ...
    private static final String TEST_DIR_PREFIX = "chatunitest-tests_";
    private static final String FAILED_TESTS_DIR_NAME = "failedtests";
    private static final Pattern TEST_METHOD_PATTERN = Pattern.compile("@Test\\s+public\\s+void\\s+([a-zA-Z0-9_]+)\\s*\\(");
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("^\\s*package\\s+([\\w.]+);");

    public TestRunnerAndReporter(Config config) {
        this.config = config;
        this.config.setValidator(new TesterValidator(
                config.getTestOutput(),
                config.getCompileOutputPath(),
                config.getProject().getBasedir().toPath().resolve("target"),
                config.getClassPaths()
        ));
    }

    /**
     * Ponto de entrada. Gera o cabeçalho para o novo formato de CSV por-método.
     */
    public void runTestsAndGenerateReport(Path projectRootPath) {
        Path outputCsvPath = Paths.get("test_results_"+config.getPluginSign()+".csv");

        try (BufferedWriter writer = Files.newBufferedWriter(outputCsvPath, StandardCharsets.UTF_8)) {
            // NOVO CABEÇALHO DO CSV (POR-MÉTODO)
            writer.write("model,Filename,TestName,Status,Log,failedtest");
            writer.newLine();

            try (Stream<Path> paths = Files.list(projectRootPath)) {
                paths.filter(path -> Files.isDirectory(path) && path.getFileName().toString().startsWith(TEST_DIR_PREFIX))
                        .forEach(testDir -> {
                            String model = testDir.getFileName().toString().substring(TEST_DIR_PREFIX.length());
                            System.out.println("Processing model: " + model);
                            processModelDirectory(testDir, model, writer);
                        });
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lógica principal atualizada para gerar uma linha por método de teste.
     */
    private void processModelDirectory(Path modelDir, String model, BufferedWriter writer) {
        try (Stream<Path> walk = Files.walk(modelDir)) {
            walk.filter(path -> path.toString().endsWith(".java") && !path.toString().endsWith("_Suite.java"))
                    .forEach(testFile -> {
                        System.out.println(" > Processing test file: " + testFile);
                        boolean isFailedTestFlag = FAILED_TESTS_DIR_NAME.equals(testFile.getParent().getFileName().toString());

                        try {
                            // Extrai as informações da classe e método SENDO TESTADOS a partir do caminho e nome do arquivo
                            TargetInfo targetInfo = extractTargetInfo(modelDir, testFile);
                            if (targetInfo == null) {
                                System.err.println("Could not parse class/method from filename: " + testFile.getFileName());
                                return; // Pula arquivos que não seguem o padrão esperado
                            }
                            System.out.println(" > Processing Class name: " + targetInfo.fullClassName);

                            // Gera o PromptInfo usando as informações corretas da classe sendo testada
                            PromptInfo promptInfo = createPromptInfo(targetInfo.fullClassName(), targetInfo.methodName());
                            String fileContent = new String(Files.readAllBytes(testFile), StandardCharsets.UTF_8);
                            String fullTestName = getFullClassName(fileContent, testFile); // Nome da classe DE TESTE
                            if (fullTestName.isEmpty()) { return; }

                            // Executa a classe de teste, agora passando o PromptInfo correto
                            ClassExecutionResult executionResult = executeTestClass(fullTestName, fileContent, promptInfo);
                            TestExecutionSummary summary = executionResult.summary();

                            // O resto da lógica para analisar o summary e escrever no CSV continua a mesma
                            List<String> allMethodsInFile = findTestMethods(fileContent);
                            if (summary == null) {
                                for (String methodName : allMethodsInFile) {
                                    writeCsvRow(writer, model, testFile.toString(), methodName, "ERROR", executionResult.log(), isFailedTestFlag);
                                }
                                return;
                            }

                            Map<String, TestExecutionSummary.Failure> failuresMap = summary.getFailures().stream()
                                    .collect(Collectors.toMap(
                                            failure -> failure.getTestIdentifier().getDisplayName().replaceAll("\\(\\)$", ""),
                                            failure -> failure,
                                            (f1, f2) -> f1
                                    ));

                            for (String methodName : allMethodsInFile) {
                                if (failuresMap.containsKey(methodName)) {
                                    TestExecutionSummary.Failure failure = failuresMap.get(methodName);
                                    Throwable ex = failure.getException();
                                    String errorLog = String.format("%s: %s",
                                            ex.getClass().getSimpleName(),
                                            ex.getMessage() != null ? ex.getMessage().replace(",", ";").replace("\n", " ") : "No message"
                                    );
                                    writeCsvRow(writer, model, testFile.toString(), methodName, "FAILED", errorLog, isFailedTestFlag);
                                } else {
                                    writeCsvRow(writer, model, testFile.toString(), methodName, "PASSED", "ok", isFailedTestFlag);
                                }
                            }

                        } catch (IOException e) {
                            System.err.println("Error processing file " + testFile + ": " + e.getMessage());
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error walking directory " + modelDir + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Helper para escrever uma linha no CSV, evitando duplicação de código.
     */
    private void writeCsvRow(BufferedWriter writer, String model, String filename, String testName, String status, String log, boolean isFailedTest) throws IOException {
        writer.write(String.join(",",
                escapeCsv(model),
                escapeCsv(filename),
                escapeCsv(testName),
                escapeCsv(status),
                escapeCsv(log),
                String.valueOf(isFailedTest)
        ));
        writer.newLine();
    }

    /**
     * Executa uma classe de teste inteira. Retorna o sumário e o log geral da execução.
     * (Esta função permanece a mesma da versão anterior, pois é eficiente).
     */
    private ClassExecutionResult executeTestClass(String fullTestName, String testSourceCode, PromptInfo promptInfo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8);
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(ps);
        System.setErr(ps);

        TestExecutionSummary summary = null;
        try {
            String testNameOnly = fullTestName.substring(fullTestName.lastIndexOf('.') + 1);
            Path compilationOutputPath = Paths.get(testNameOnly + "_Compilation.txt");
            ps.println("Test name "+testNameOnly);
            // A variável 'promptInfo' agora vem como parâmetro
            boolean compileResult = config.getValidator().semanticValidate(testSourceCode, testNameOnly, compilationOutputPath, promptInfo);

            // ... resto do método sem alterações ...
            if (compileResult) {
                summary = config.getValidator().execute(fullTestName);
                if (summary == null) {
                    ps.println(">> TEST EXECUTION TIMEOUT <<");
                }
            } else {
                ps.println(">> TEST COMPILATION FAILED <<");
            }
        } catch (Exception e) {
            ps.println(">> An unexpected error occurred during test execution <<");
            e.printStackTrace(ps);
        } finally {
            System.out.flush();
            System.setOut(oldOut);
            System.setErr(oldErr);
        }

        String log = baos.toString(StandardCharsets.UTF_8).trim();
        return new ClassExecutionResult(summary, log.isEmpty() ? "ok" : log);
    }

    // Record para organizar os dados da execução da classe
    private record ClassExecutionResult(TestExecutionSummary summary, String log) {}

    // Métodos auxiliares (findTestMethods, getFullClassName, escapeCsv) continuam os mesmos
    private List<String> findTestMethods(String fileContent) {
        List<String> methods = new ArrayList<>();
        Matcher matcher = TEST_METHOD_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            methods.add(matcher.group(1));
        }
        return methods;
    }

    private String getFullClassName(String fileContent, Path javaFile) {
        Pattern p = PACKAGE_PATTERN;
        Matcher m = p.matcher(fileContent);
        String packageName = "";
        if (m.find()) {
            packageName = m.group(1);
        }
        String className = javaFile.getFileName().toString().replace(".java", "");
        return packageName.isEmpty() ? className : packageName + "." + className;
    }

    private String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }


    private record TargetInfo(String fullClassName, String methodName) {}

    private TargetInfo extractTargetInfo(Path modelDir, Path testFile) {
        String fileName = testFile.getFileName().toString().replace(".java", "");
        String[] parts = fileName.split("_");

        if (parts.length < 2) {
            return null; // O nome do arquivo não segue o padrão esperado
        }

        String className = parts[0];
        String methodName = parts[1];

        // >>> ALTERAÇÃO AQUI <<<
        // Determina o caminho base para o pacote, ignorando a pasta 'failedtest'
        Path packagePathSource = testFile.getParent();
        if (FAILED_TESTS_DIR_NAME.equals(packagePathSource.getFileName().toString())) {
            packagePathSource = packagePathSource.getParent(); // Sobe um nível no diretório
        }

        // Calcula o caminho relativo para derivar o pacote a partir da fonte correta
        Path relativePath = modelDir.relativize(packagePathSource);
        if (relativePath.getNameCount() > 1) {
            relativePath = relativePath.subpath(1, relativePath.getNameCount());
        } else {
            // Se tiver apenas um componente (ou nenhum), o pacote resultante é vazio.
            relativePath = Paths.get("");
        }
        String packageName = relativePath.toString().replace(File.separator, ".");

        // Evita adicionar um "." no início se o pacote estiver na raiz
        String fullClassName = packageName.isEmpty() || ".".equals(packageName) ? className : packageName + "." + className;

        return new TargetInfo(fullClassName, methodName);
    }


    /**
     * Cria o PromptInfo com base no fullClassName e methodName da classe SENDO TESTADA.
     * Esta é a implementação correta da lógica do seu exemplo original.
     */
    private PromptInfo createPromptInfo(String fullClassName, String methodName) {
        try {
            ClassRunner cs = new ClassRunner(config, fullClassName);
            ClassInfo classInfo = cs.classInfo;

            if (classInfo == null || classInfo.methodSigs.isEmpty()) {
                System.err.println("Could not find class info or methods for: " + fullClassName);
                return null;
            }

            // Encontra a assinatura completa do método para evitar ambiguidades
            String methodSignature = classInfo.methodSigs.keySet().stream()
                    .filter(sig -> sig.startsWith(methodName + "("))
                    .findFirst()
                    .orElse(null);

            if (methodSignature == null) {
                System.err.println("Method '" + methodName + "' not found in class: " + fullClassName);
                return null;
            }

            MethodInfo methodInfo = ClassRunner.getMethodInfo(config, classInfo, methodSignature);
            return AbstractRunner.generatePromptInfoWithoutDep(config, classInfo, methodInfo);

        } catch (Exception e) {
            System.err.println("Error creating PromptInfo for " + fullClassName + ": " + e.getMessage());
            return null;
        }
    }
}