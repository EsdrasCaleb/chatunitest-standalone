package zju.cst.aces.ant;

import zju.cst.aces.api.config.Config;
import zju.cst.aces.dto.PromptInfo;
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
                    int[] mutationResults = performMutationTest(className, methodName, testFile);
                    writer.write(testFile + "," + String.join(",",
                            Arrays.stream(mutationResults).mapToObj(String::valueOf).toArray(String[]::new)));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] performMutationTest(String fullClassName, String methodName, String testFile) {
        int tests = 0;
        int[] mutationResults = new int[]{-1, -1, -1, -1, -1, -1};

        Path testPath = Paths.get(testFile);
        String fullTestName = testPath.getFileName().toString().replace(".java", "");


        TestProcessor testProcessor = new TestProcessor(fullTestName);
        String finalCode = null;
        try {
            finalCode = new String(Files.readAllBytes(testPath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MutationOperatorUtil.extractMethodSignature(finalCode, methodName);
        PromptInfo promptInfo = new PromptInfo(false, fullClassName, methodName, "");

        String mutatedClassName = promptInfo.className + "_mutated";

        finalCode = MutationOperatorUtil.changeClassName(finalCode, promptInfo.className, mutatedClassName);


        String[] mutationTypes = {
                "Null", "Variable", "Boolean", "Arithmetic", "Logic", "Relational"
        };

        for (int i = 0; i < mutationTypes.length; i++) {
            System.out.println("Testing " + mutationTypes[i] + " mutation");
            String mutatedCode = applyMutation(mutationTypes[i], promptInfo, mutatedClassName);

            if (!mutatedCode.isEmpty()) {
                try {
                    int[] result = runMutation(fullTestName, promptInfo, MutationOperatorUtil.injectMutationClass(finalCode, mutatedCode), testProcessor, config);
                    tests = Math.max(tests, result[0]);
                    mutationResults[i] = result[1];
                } catch (Exception e) {
                    System.err.println("Error in " + mutationTypes[i] + " mutation: " + e.getMessage());
                }
            }
        }
        return new int[]{tests, mutationResults[0], mutationResults[1], mutationResults[2], mutationResults[3], mutationResults[4], mutationResults[5]};
    }

    private String applyMutation(String type, PromptInfo promptInfo, String mutatedClassName) {
        switch (type) {
            case "Null":
                return MutationOperatorUtil.applyNullMutation(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName);
            case "Variable":
                return MutationOperatorUtil.applyVariableMutation(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName);
            case "Boolean":
                return MutationOperatorUtil.applyOperatorMutationBoolean(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName);
            case "Arithmetic":
                return MutationOperatorUtil.applyOperatorMutationAritimetic(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName);
            case "Logic":
                return MutationOperatorUtil.applyOperatorMutationLogic(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName);
            case "Relational":
                return MutationOperatorUtil.applyOperatorMutationRelational(promptInfo.getClassInfo().compilationUnitCode,
                        promptInfo.getMethodInfo().methodName, promptInfo.className, mutatedClassName);
            default:
                return "";
        }
    }
}
