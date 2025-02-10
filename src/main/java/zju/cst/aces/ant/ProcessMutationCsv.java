package zju.cst.aces.ant;

import zju.cst.aces.api.config.Config;
import zju.cst.aces.dto.ClassInfo;
import zju.cst.aces.dto.MethodInfo;
import zju.cst.aces.dto.PromptInfo;
import zju.cst.aces.runner.AbstractRunner;
import zju.cst.aces.runner.ClassRunner;
import zju.cst.aces.runner.solution_runner.BenchmarkRunner;
import zju.cst.aces.util.MutationOperatorUtil;
import zju.cst.aces.util.TestProcessor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import static zju.cst.aces.runner.solution_runner.BenchmarkRunner.runMutation;

public class ProcessMutationCsv {
    private final Config config;

    public ProcessMutationCsv(Config config) {
        this.config = config;
    }

    public void makeNewCsv(String fileName) {
        String outputFileName = fileName.replace(".csv", "_mutations.csv");

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName))) {

            String header = reader.readLine();
            if (header == null) {
                System.err.println("CSV file is empty");
                return;
            }

            List<String> headers = Arrays.asList(header.split(","));
            int classIndex = headers.indexOf("class");
            int methodIndex = headers.indexOf("method");
            int resultIndex = headers.indexOf("result");
            int fileIndex = headers.indexOf("file");

            if (classIndex < 0 || methodIndex < 0 || resultIndex < 0 || fileIndex < 0) {
                System.err.println("Invalid CSV format. Missing required columns.");
                return;
            }

            writer.write("file,mutation_result");
            writer.newLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length <= Math.max(classIndex, Math.max(methodIndex, Math.max(resultIndex, fileIndex)))) {
                    continue;
                }

                String className = values[classIndex];
                String methodName = values[methodIndex];
                String testResult = values[resultIndex];
                String testFile = values[fileIndex];

                if (!"FAILURE".equalsIgnoreCase(testResult)) {
                    int[] mutationResults;
                    if(methodName.equals("*")) {
                        mutationResults = performMutationTest(className, testFile,writer);
                    }
                    else{
                        mutationResults = performMutationTest(className, methodName, testFile);
                        writer.write(testFile + "," + String.join(",",
                                Arrays.stream(mutationResults).mapToObj(String::valueOf).toArray(String[]::new)));
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[]  performMutationTest(String fullClassName,String testFile, BufferedWriter writer) throws IOException {
        ClassRunner cs;
        try {
            cs = new ClassRunner(config, fullClassName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] methodNames = extractMethodNames(cs.classInfo.methodSigs);
        int[] finalResult = new int[]{-1, -1, -1, -1, -1, -1,-1};
        for (String methodName : methodNames) {
            int[] mutationResults = performMutationTest(fullClassName, methodName, testFile);
            writer.write(testFile + "," + String.join(",",
                    Arrays.stream(mutationResults).mapToObj(String::valueOf).toArray(String[]::new)));
            writer.newLine();
        }
        return finalResult;

    }

    private int[] performMutationTest(String fullClassName, String methodName, String testFile) throws IOException {
        int tests = 0;
        int[] mutationResults = new int[]{-1, -1, -1, -1, -1, -1,-1};

        Path testPath = Paths.get(testFile);
        String fullTestName = extractPackage(fullClassName)+testPath.getFileName().toString().replace(".java", "");


        TestProcessor testProcessor = new TestProcessor(fullTestName);
        String finalCode = null;
        try {
            finalCode = new String(Files.readAllBytes(testPath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String classpath = extractBasePath(testPath);
        String classCode;
        Path classCodePath = Paths.get(classpath+"/src/main/java/"+fullClassName.replace('.', '/') + ".java");
        try {
            classCode = new String(Files.readAllBytes(classCodePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(classCodePath);
            throw new RuntimeException(e);
        }

        ClassRunner cs;
        try {
            cs = new ClassRunner(config, fullClassName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String methodSignature = "";
        for (Map.Entry<String, String> entry : cs.classInfo.methodSigs.entrySet()) {
            if (entry.getKey().startsWith(methodName + "(")) {
                methodSignature = entry.getKey(); // Return the matching key
            }
        }
        if(methodSignature.isEmpty()){
            return mutationResults;
        }
        //Class fullname:com.ib.client.UnderComp method:equals signature:equals(Object)
        System.out.println("Class fullname:"+fullClassName+" method:"+methodName+" signature:"+methodSignature);

        MethodInfo methodInfo = ClassRunner.getMethodInfo(config, cs.classInfo, methodSignature);

        PromptInfo promptInfo = AbstractRunner.generatePromptInfoWithoutDep(config,cs.classInfo,methodInfo);

        String mutatedClassName = promptInfo.className + "_mutated";

        finalCode = MutationOperatorUtil.changeClassName(finalCode, promptInfo.className, mutatedClassName);

        String[] mutationTypes = {
                "Null", "Variable", "Boolean", "Arithmetic", "Logic", "Relational"
        };

        for (int i = 0; i < mutationTypes.length; i++) {
            System.out.println("Testing " + mutationTypes[i] + " mutation");
            String mutatedCode = applyMutation(mutationTypes[i], promptInfo, mutatedClassName,false);
            if (!mutatedCode.isEmpty()) {
                try {
                    int[] result = runMutation(fullTestName.trim(), promptInfo, MutationOperatorUtil.injectMutationClass(finalCode, mutatedCode), testProcessor, config);
                    tests = Math.max(tests, result[0]);
                    if(result[0] != -1){
                        mutationResults[i] = result[1];
                    }
                    else {
                        String finalCodeMutated = MutationOperatorUtil.changeMethodName(finalCode, methodInfo.methodName, methodInfo.methodName+"_mutated");
                        mutatedCode = applyMutation(mutationTypes[i], promptInfo, mutatedClassName,true);
                        result = runMutation(fullTestName.trim(), promptInfo, MutationOperatorUtil.injectMutationClass(finalCodeMutated, mutatedCode), testProcessor, config);
                        tests = Math.max(tests, result[0]);
                        if(result[0] != -1){
                            mutationResults[i] = result[1];
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error in " + mutationTypes[i] + " mutation: " + e.getMessage());
                }
            }
        }
        return new int[]{tests, mutationResults[0], mutationResults[1], mutationResults[2], mutationResults[3], mutationResults[4], mutationResults[5]};
    }

    private String applyMutation(String type, PromptInfo promptInfo, String mutatedClassName, Boolean mutate_method) {
        switch (type) {
            case "Null":
                return MutationOperatorUtil.applyNullMutation(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName,mutate_method);
            case "Variable":
                return MutationOperatorUtil.applyVariableMutation(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName,mutate_method);
            case "Boolean":
                return MutationOperatorUtil.applyOperatorMutationBoolean(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName,mutate_method);
            case "Arithmetic":
                return MutationOperatorUtil.applyOperatorMutationAritimetic(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName,mutate_method);
            case "Logic":
                return MutationOperatorUtil.applyOperatorMutationLogic(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName,mutate_method);
            case "Relational":
                return MutationOperatorUtil.applyOperatorMutationRelational(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName,mutate_method);
            default:
                return "";
        }
    }

    public static String extractBasePath(Path filePath) {
        Path path = filePath.normalize();
        String[] parts = path.toString().split("/|\\\\"); // Support both Unix and Windows paths

        if (parts.length < 3) {
            return "Invalid path format";
        }

        // Extract first three parts: "../SF110/1_tullibee/"
        return String.join("/", parts[0], parts[1], parts[2]) + "/";
    }

    public static String extractPackage(String fullClassName) {
        int lastDotIndex = fullClassName.lastIndexOf(".");
        return (lastDotIndex != -1) ? fullClassName.substring(0, lastDotIndex + 1) : "";
    }

    public static String[] extractMethodNames(Map<String, String> methodSignatures) {
        return methodSignatures.keySet().stream()
                .map(sig -> sig.split("\\(")[0]) // Extract method name before '('
                .toArray(String[]::new);
    }
}
