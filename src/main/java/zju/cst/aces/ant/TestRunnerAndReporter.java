package zju.cst.aces.ant;

import org.junit.platform.launcher.listeners.TestExecutionSummary;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.util.TestProcessor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestRunnerAndReporter {

    private final Config config;
    private static final String TEST_DIR_PREFIX = "chatunitest-tests_";
    private static final String FAILED_TESTS_DIR_NAME = "failedtests";
    private static final Pattern TEST_METHOD_PATTERN = Pattern.compile("@Test\\s+public\\s+void\\s+([a-zA-Z0-9_]+)\\s*\\(");
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("^\\s*package\\s+([\\w.]+);");

    public TestRunnerAndReporter(Config config) {
        this.config = config;
    }

    /**
     * Main method to scan directories and run tests.
     * @param projectRootPath The root directory of the project to scan.
     */
    public void runTestsAndGenerateReport(Path projectRootPath) {
        Path outputCsvPath = Paths.get("test_results.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(outputCsvPath, StandardCharsets.UTF_8)) {
            // Write CSV header
            writer.write("model,Filename,TestName,TestLog,TestPassed,failedtest");
            writer.newLine();

            // Find all directories with the specified prefix
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
     * Process a single model directory to find and run tests.
     * @param modelDir The directory for a specific model.
     * @param model The name of the model.
     * @param writer The CSV writer.
     */
    private void processModelDirectory(Path modelDir, String model, BufferedWriter writer) {
        try (Stream<Path> walk = Files.walk(modelDir)) {
            walk.filter(path -> path.toString().endsWith(".java"))
                    .forEach(testFile -> {
                        System.out.println(" > Found test file: " + testFile);
                        boolean isFailedTest = FAILED_TESTS_DIR_NAME.equals(testFile.getParent().getFileName().toString());

                        try {
                            String fileContent = new String(Files.readAllBytes(testFile), StandardCharsets.UTF_8);
                            String fullClassName = getFullClassName(fileContent, testFile);
                            if (fullClassName.isEmpty()) {
                                System.err.println("Could not determine full class name for: " + testFile);
                                return;
                            }

                            List<String> testMethods = findTestMethods(fileContent);
                            if (testMethods.isEmpty()) {
                                System.out.println("   - No @Test methods found in " + testFile.getFileName());
                                return;
                            }

                            for (String testName : testMethods) {
                                System.out.println("   - Running test: " + testName);

                                // >>>>> ESTA É A LINHA ATUALIZADA <<<<<
                                ExecutionResult result = executeSingleTest(fullClassName, testName, testFile);

                                // Write result to CSV
                                writer.write(String.join(",",
                                        escapeCsv(model),
                                        escapeCsv(testFile.toString()),
                                        escapeCsv(testName),
                                        escapeCsv(result.log()),
                                        String.valueOf(result.passed()),
                                        String.valueOf(isFailedTest)
                                ));
                                writer.newLine();
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
     * Executes a single test method by compiling its source file and then running it.
     * This corrected version mirrors the logic from your project's BenchmarkRunner.
     * @param fullTestName The fully qualified name of the test class.
     * @param methodName The name of the test method to check in the results.
     * @param testFile The Path to the .java source file of the test.
     * @return An ExecutionResult record with the log and pass/fail status.
     */
    private ExecutionResult executeSingleTest(String fullTestName, String methodName, Path testFile) {
        // 1. Redirect standard output and error to capture logs
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8);
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(ps);
        System.setErr(ps);

        boolean testPassed = false;
        try {
            // 2. Read the source code from the test file
            String testSourceCode;
            try {
                testSourceCode = new String(Files.readAllBytes(testFile), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read test file: " + testFile, e);
            }

            String testNameOnly = fullTestName.substring(fullTestName.lastIndexOf('.') + 1);

            // 3. Compile the test source code using the validator
            // The last two nulls are placeholders for paths and promptInfo, not needed here.
            boolean compileResult = config.getValidator().semanticValidate(testSourceCode, testNameOnly, null, null);

            if (!compileResult) {
                ps.println(">> TEST COMPILATION FAILED <<");
                // The compilation error is already captured in the PrintStream (baos)
                // by the underlying validator.
                testPassed = false;
            } else {
                // 4. If compilation succeeds, execute the test
                // We execute the whole test class, as the validator likely expects.
                TestExecutionSummary summary = config.getValidator().execute(fullTestName);

                if (summary == null) {
                    ps.println(">> TEST EXECUTION TIMEOUT <<");
                    testPassed = false;
                } else {
                    // 5. Check the summary for failures related to the specific method.
                    // A simple check is if any test failed at all.
                    if (summary.getTestsFailedCount() > 0) {
                        testPassed = summary.getFailures().stream()
                                .noneMatch(failure -> failure.getTestIdentifier().getDisplayName().startsWith(methodName + "("));
                        if (!testPassed) {
                            ps.println(">> TEST METHOD FAILED: " + methodName + " <<");
                        }
                    } else {
                        testPassed = true; // No tests failed in the class
                    }
                }
            }
        } catch (Exception e) {
            // Any other exception during the process is a failure.
            e.printStackTrace(ps); // Log the exception stack trace
            testPassed = false;
        } finally {
            // 6. Restore original streams
            System.out.flush();
            System.setOut(oldOut);
            System.setErr(oldErr);
        }

        String log = baos.toString(StandardCharsets.UTF_8).trim();
        return new ExecutionResult(log, testPassed);
    }

    /**
     * Parses a Java file content to find method names annotated with @Test.
     * @param fileContent The content of the Java file.
     * @return A list of test method names.
     */
    private List<String> findTestMethods(String fileContent) {
        List<String> methods = new ArrayList<>();
        Matcher matcher = TEST_METHOD_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            methods.add(matcher.group(1));
        }
        return methods;
    }

    /**
     * Determines the fully qualified class name from the file content and path.
     * @param fileContent The content of the Java file.
     * @param javaFile The path to the Java file.
     * @return The fully qualified class name (e.g., com.example.MyTest).
     */
    private String getFullClassName(String fileContent, Path javaFile) {
        Matcher matcher = PACKAGE_PATTERN.matcher(fileContent);
        String packageName = "";
        if (matcher.find()) {
            packageName = matcher.group(1);
        }

        String className = javaFile.getFileName().toString().replace(".java", "");

        return packageName.isEmpty() ? className : packageName + "." + className;
    }

    /**
     * Escapes a string for CSV format, handling commas and quotes.
     */
    private String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        return value;
    }

    /**
     * A simple record to hold the result of a test execution.
     */
    private record ExecutionResult(String log, boolean passed) {}

}