package ufrn.chattester;

import org.junit.platform.launcher.listeners.TestExecutionSummary;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.api.impl.ChatGenerator;
import zju.cst.aces.api.impl.PromptConstructorImpl;
import zju.cst.aces.dto.*;
import zju.cst.aces.prompt.PromptTemplate;
import zju.cst.aces.runner.AbstractRunner;
import zju.cst.aces.runner.MethodRunner;
import zju.cst.aces.util.CodeExtractor;
import zju.cst.aces.util.TestProcessor;

import ufrn.chattester.ModelChatGenerator;
import ufrn.chattester.CustomRepairImpl;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TesterMethodRunner extends MethodRunner {

    public TesterMethodRunner(Config config, String fullClassName, MethodInfo methodInfo) throws IOException {
        super(config, fullClassName, methodInfo);
    }

    /**
     * Main process of ChatTester, including:
     * 1. Generate intention for focal method, then
     * 2. Use intention and focal context to generate test, and
     * 3. Iteratively repair the test until it passes.
     * @param num
     * @return If the generation process is successful
     * @throws IOException
     */
    @Override
    public boolean startRounds(final int num) throws IOException {
        String testName = className + separator + methodInfo.methodName + separator
                + classInfo.methodSigs.get(methodInfo.methodSignature) + separator + num + separator + "Test";
        String fullTestName = fullClassName + separator + methodInfo.methodName + separator
                + classInfo.methodSigs.get(methodInfo.methodSignature) + separator + num + separator + "Test";
        config.getLog().info("\n==========================\n[ChatUniTest] Generating test for method < "
                + methodInfo.methodName + " > number " + num + "...\n");
        
        //ModelChatGenerator generator = new ModelChatGenerator(config); // this do anything?
        PromptConstructorImpl pc = new PromptConstructorImpl(config);
        CustomRepairImpl repair = new CustomRepairImpl(config, pc);

        if (!methodInfo.dependentMethods.isEmpty()) {
            pc.setPromptInfoWithDep(classInfo, methodInfo);
        } else {
            pc.setPromptInfoWithoutDep(classInfo, methodInfo);
        }
        pc.setFullTestName(fullTestName);
        pc.setTestName(testName);

        PromptInfo promptInfo = pc.getPromptInfo();
        promptInfo.setFullTestName(fullTestName);
        Path savePath = config.getTestOutput().resolve(fullTestName.replace(".", File.separator) + ".java");
        promptInfo.setTestPath(savePath);

        int errorNum = Integer.MAX_VALUE;
        int invalidRefinementCount = 0;
        for (int rounds = 0; rounds < config.getMaxRounds(); rounds++) {
            promptInfo.addRecord(new RoundRecord(rounds));
            RoundRecord record = promptInfo.getRecords().get(rounds);
            record.setAttempt(num);
            List<Message> prompt;
            PromptTemplate pt = this.promptGenerator.promptTemplate;
            config.getLog().warn("Path:"+config.getExamplePath()+" cname "+promptInfo.className);

            pt.buildDataModel(config, promptInfo);

            if (rounds == 0) {
                // generate method intention
                config.getLog().info("Creating intention for method < " + methodInfo.methodName + " > ...");
                config.getLog().warn("Prompt Temp " + pt.TEMPLATE_EXTRA);
                config.getLog().warn("Error:"+promptInfo.errorMsg);
                List<Message> intentionPrompt = this.promptGenerator.generateMessages(promptInfo, pt.TEMPLATE_EXTRA);
                config.getLog().info("Using model");
                ChatResponse response = ModelChatGenerator.chat(config, intentionPrompt);
                config.getLog().info("Gotten response"+response.toString());
                String intention = ModelChatGenerator.getContentByResponse(response);
                config.getLog().info("Pass intention");
                // set intention in user prompt
                prompt = promptGenerator.generateMessages(promptInfo);
                Message userMessage = prompt.get(1);
                String oldContent = userMessage.getContent();
                int lastBraceIndex = oldContent.lastIndexOf("}");
                userMessage.setContent(
                    new StringBuilder(oldContent).insert(lastBraceIndex + 1, "\n//Method intention\n" + intention).toString()
                );

                config.getLog().info("Generating test for method < " + methodInfo.methodName + " > round " + rounds + " ...");
            } else if (promptInfo.getErrorMsg() != null) {
                assert(!promptInfo.getErrorMsg().getErrorMessage().isEmpty());
                if (promptInfo.getErrorMsg().getErrorMessage().size() >= errorNum) {
                    invalidRefinementCount++;
                    if (invalidRefinementCount >= 3) {
                        config.getLog().info("Exceeding maximum invalid refinement count, break.");
                        break;
                    }
                }
                errorNum = promptInfo.getErrorMsg().getErrorMessage().size();
                // iterate repair process
                config.getLog().info("Fixing test for method < " + methodInfo.methodName + " > round " + rounds + " ...");
                prompt = promptGenerator.generateMessages(promptInfo);
                TestMessage errorMsg = promptInfo.getErrorMsg();
                if (errorMsg.getErrorType().equals(TestMessage.ErrorType.COMPILE_ERROR)) {
                    List<CompilerError> compilerErrors = new ArrayList<>();
                    for (String error : errorMsg.getErrorMessage()) {
                        compilerErrors.addAll(parseCompilerErrors(error));
                    }
                    Set<String> classInError = new HashSet<>();
                    Map<String, String> methodInError = new HashMap<>();
                    for (CompilerError error : compilerErrors) {
                        if (error.symbolType != null && error.symbolType.equals("class")) {
                            classInError.add(error.symbolName);
                        } else if (error.symbolType != null && error.symbolType.equals("method")) {
                            methodInError.put(error.symbolName, error.variableType);
                        }
                    }

                    String repairPrompt = prompt.get(0).getContent();
                    StringBuilder deps = new StringBuilder();

                    for (String className : classInError) {
                        ClassInfo depInfo = AbstractRunner.getClassInfo(config, className);
                        if (depInfo != null) {
                            deps.append("// ").append(className).append(" class\n");
                            deps.append(depInfo.getClassSignature()).append("{\n");
                            deps.append(joinLines(depInfo.getConstructorSigs())).append("\n}");
                        }
                    }
                    for (String methodName : methodInError.keySet()) {
                        String methodType = methodInError.get(methodName);
                        if (deps.toString().contains(methodType)) {
                            continue;
                        }
                        ClassInfo typeInfo = AbstractRunner.getClassInfo(config, methodType);
                        deps.append("// ").append(methodType).append(" class\n");
                        deps.append(typeInfo.getClassSignature()).append("{\n");
                        MethodInfo depInfo = null;
                        for (String mSig : typeInfo.getMethodSigs().keySet()) {
                            if (mSig.split("\\(")[0].equals(methodName.split("\\(")[0])) {
                                depInfo = AbstractRunner.getMethodInfo(config, typeInfo, mSig);
                                if (depInfo != null) {
                                    deps.append(depInfo.methodSignature).append(";\n");
                                }
                            }
                        }
                        if (depInfo == null) {
                            deps.append(joinLines(typeInfo.getMethodsBrief()));
                        }
                        deps.append("}");
                    }

                    if (!deps.toString().isEmpty()) {
//                        config.getLog().info("==================================================");
//                        config.getLog().info("[ChatTester Deps in Repair Process]: \n" + deps);
//                        config.getLog().info("==================================================");
                        int lastBraceIndex = repairPrompt.lastIndexOf("}");
                        prompt.get(0).setContent(
                                new StringBuilder(repairPrompt).insert(lastBraceIndex + 1, deps).toString()
                        );
                    }
                }
                config.getLog().warn("Prompt de correcao:"+prompt.toString());
            } else {
                prompt = promptGenerator.generateMessages(promptInfo);
            }
            //config.getLog().info("Tring to generate tests with my function ");
            // start generate test
            String code = generateTest(prompt, record);
            if (!record.isHasCode()) {
                continue;
            }
            config.getLog().info("Passou o tem codigo");
            if (CodeExtractor.isTestMethod(code)) {
                TestSkeleton skeleton = new TestSkeleton(promptInfo); // test skeleton to wrap a test method
                code = skeleton.build(code);
            } else {
                code = repair.ruleBasedRepair(code);
            }
            config.getLog().info("Entrou em unity test");
            promptInfo.setUnitTest(code);

            record.setCode(code);
            config.getLog().info("Reparacao");
            repair.LLMBasedRepair(code, record.getRound());
            
            if (repair.isSuccess()) {
                config.getLog().info("Reparou o codigo");
                record.setHasError(false);
                exportRecord(promptInfo, classInfo, record.getAttempt());
                config.getLog().info("Retornando");
                return true;
            }
            config.getLog().info("Reparo falhou");

            record.setHasError(true);
            record.setErrorMsg(promptInfo.getErrorMsg());
        }
        exportRecord(pc.getPromptInfo(), classInfo, num);
        return false;
    }

    public static class CompilerError {
        public String testName;
        public int lineNumber;
        public String symbolType;
        public String symbolName;
        public String variableType;
        public String variableName;
        public String locationDetail;

        @Override
        public String toString() {
            return "ErrorLocation: " + testName + ", LineNumber: " + lineNumber
                    + ", SymbolType: " + symbolType + ", SymbolName: " + symbolName
                    + ", VariableType: " + variableType + ", VariableName: " + variableName;
        }
    }

    public static List<CompilerError> parseCompilerErrors(String errorMessages) {
        List<CompilerError> errors = new ArrayList<>();
        String pattern = "Error in (.+?): line (\\d+) : (cannot find symbol|找不到符号)\\n\\s+(符号|symbol):\\s+(方法|变量|类|method|variable|class) ([^\\n]+)\\n\\s+(位置|location): ([^\\n]+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(errorMessages);

        while (m.find()) {
            CompilerError error = new CompilerError();
            error.testName = m.group(1);
            error.lineNumber = Integer.parseInt(m.group(2));
            error.symbolType = m.group(5);
            error.symbolName = m.group(6).trim();

            if (error.symbolType.equals("类")) {
                error.symbolType = "class";
            } else if (error.symbolType.equals("方法")) {
                error.symbolType = "method";
            } else if (error.symbolType.equals("变量")) {
                error.symbolType = "variable";
            }

            error.locationDetail = m.group(8).trim();
            if (error.symbolType.equals("method")) {
                if (error.locationDetail.contains("类型为 ")) {
                    // 解析中文错误信息中的位置信息
                    Pattern locationPattern = Pattern.compile("类型为 (\\S+)的变量 (\\S+)");
                    Matcher locationMatcher = locationPattern.matcher(error.locationDetail);
                    if (locationMatcher.find()) {
                        error.variableType = locationMatcher.group(1);
                        error.variableName = locationMatcher.group(2);
                    }
                } else if (error.locationDetail.contains("类 ")) {
                    // 如果是类错误，我们将解析出类的全限定名
                    Pattern locationPattern = Pattern.compile("类 (\\S+)");
                    Matcher locationMatcher = locationPattern.matcher(error.locationDetail);
                    if (locationMatcher.find()) {
                        error.variableType = locationMatcher.group(1);
                        // 如果是类错误，则没有可获取的变量名
                        error.variableName = "";
                    }
                } else if (error.locationDetail.contains("class ")) {
                    // 如果是类错误，我们将解析出类的全限定名
                    Pattern locationPattern = Pattern.compile("class (\\S+)");
                    Matcher locationMatcher = locationPattern.matcher(error.locationDetail);
                    if (locationMatcher.find()) {
                        error.variableType = locationMatcher.group(1);
                        // 如果是类错误，则没有可获取的变量名
                        error.variableName = "";
                    }
                } else if (error.locationDetail.contains("variable ")) {
                    // 如果错误与变量相关，我们同时解析变量的名称和类型。
                    Pattern locationPattern = Pattern.compile("variable (\\S+) of type (\\S+)");
                    Matcher locationMatcher = locationPattern.matcher(error.locationDetail);
                    if (locationMatcher.find()) {
                        error.variableName = locationMatcher.group(1);
                        error.variableType = locationMatcher.group(2);
                    }
                }
            }

            errors.add(error);
        }

        return errors;
    }

    @Override
    public String generateTest(List<Message> prompt, RoundRecord record) throws IOException {
        if (isExceedMaxTokens(config.getMaxPromptTokens(), prompt)) {
            config.getLog().error("Exceed max prompt tokens: " + methodInfo.methodName + " Skipped.");
            return "";
        }
        config.getLog().debug("[Prompt]:\n" + prompt.toString());

        ChatResponse response = ModelChatGenerator.chat(config, prompt);
        String content = ModelChatGenerator.getContentByResponse(response);
        String code = ModelChatGenerator.extractCodeByContent(content);
        
        record.setPromptToken(response.getUsage().getPromptTokens());
        record.setResponseToken(response.getUsage().getCompletionTokens());
        record.setPrompt(prompt);
        record.setResponse(content);
        
        if (code.isEmpty()) {
            config.getLog().error("[Response]:\n" + content);
            config.getLog().error("Test for method < " + methodInfo.methodName + " > extract code failed in generation");
            record.setHasCode(false);
            return "";
        }
        record.setHasCode(true);
        config.getLog().info("Code retrived returning");
        return code;
    }

    public static boolean runTest(Config config, String fullTestName, PromptInfo promptInfo, int rounds) {
        String testName = fullTestName.substring(fullTestName.lastIndexOf(".") + 1);
        Path savePath = config.getTestOutput().resolve(fullTestName.replace(".", File.separator) + ".java");
        if (promptInfo.getTestPath() == null) {
            promptInfo.setTestPath(savePath);
        }
        config.getLog().warn(fullTestName);
        TestProcessor testProcessor = new TestProcessor(fullTestName);
        String code = promptInfo.getUnitTest();
        if (rounds >= 1) {
            code = testProcessor.addCorrectTest(promptInfo);
        }
        config.getLog().warn("<codigo>\n"+code+"</codigo>");
        // Compilation
        Path compilationErrorPath = config.getErrorOutput().resolve(testName + "_CompilationError_" + rounds + ".txt");
        Path executionErrorPath = config.getErrorOutput().resolve(testName + "_ExecutionError_" + rounds + ".txt");
        boolean compileResult = config.getValidator().semanticValidate(code, testName, compilationErrorPath, promptInfo);
        if (!compileResult) {
            config.getLog().info("Test for method < " + promptInfo.getMethodInfo().getMethodName() + " > compilation failed round " + rounds);
            return false;
        }
        if (config.isNoExecution()) {
            exportTest(code, savePath);
            config.getLog().info("Test for method < " + promptInfo.getMethodInfo().getMethodName() + " > generated successfully round " + rounds);
            return true;
        }
        
        // Execution
        TestExecutionSummary summary = config.getValidator().execute(fullTestName);
        if (summary.getTestsFailedCount() > 0 || summary.getTestsSucceededCount() == 0) {
            String testProcessed = testProcessor.removeErrorTest(promptInfo, summary);

            // Remove errors successfully, recompile and re-execute test
            if (testProcessed != null) {
                config.getLog().debug("[Original Test]:\n" + code);
                if (config.getValidator().semanticValidate(testProcessed, testName, compilationErrorPath, null)) {
                    if (config.getValidator().runtimeValidate(fullTestName)) {
                        exportTest(testProcessed, savePath);
                        config.getLog().debug("[Processed Test]:\n" + testProcessed);
                        config.getLog().info("Processed test for method < " + promptInfo.getMethodInfo().getMethodName() + " > generated successfully round " + rounds);
                        return true;
                    }
                }
                testProcessor.removeCorrectTest(promptInfo, summary);
            }

            // Set promptInfo error message
            TestMessage testMessage = new TestMessage();
            List<String> errors = new ArrayList<>();
            summary.getFailures().forEach(failure -> {
                for (StackTraceElement st : failure.getException().getStackTrace()) {
                    if (st.getClassName().contains(fullTestName)) {
                        errors.add("Error in " + failure.getTestIdentifier().getLegacyReportingName()
                                + ": line " + st.getLineNumber() + " : "
                                + failure.getException().toString());
                    }
                }
            });
            testMessage.setErrorType(TestMessage.ErrorType.RUNTIME_ERROR);
            testMessage.setErrorMessage(errors);
            promptInfo.setErrorMsg(testMessage);
            exportError(code, errors, executionErrorPath);
            testProcessor.removeCorrectTest(promptInfo, summary);
            config.getLog().info("Test for method < " + promptInfo.getMethodInfo().getMethodName() + " > execution failed round " + rounds);
            return false;
        }
//            summary.printTo(new PrintWriter(System.out));
        exportTest(code, savePath);
        config.getLog().info("Test for method < " + promptInfo.getMethodInfo().getMethodName() + " > compile and execute successfully round " + rounds);
        return true;
    }

    /* 
    public void exportRecord(PromptInfo promptInfo, ClassInfo classInfo, int attempt) {
        String methodIndex = classInfo.methodSigs.get(promptInfo.methodSignature);
        Path recordPath = config.getHistoryPath();
        config.getLog().info("Test for method save path: " + recordPath.toString());


        recordPath = recordPath.resolve("class" + classInfo.index);
        exportMethodMapping(classInfo, recordPath);

        recordPath = recordPath.resolve("method" + methodIndex);
        exportAttemptMapping(promptInfo, recordPath);

        recordPath = recordPath.resolve("attempt" + attempt);
        if (!recordPath.toFile().exists()) {
            recordPath.toFile().mkdirs();
        }
        File recordFile = recordPath.resolve("records.json").toFile();
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(recordFile), StandardCharsets.UTF_8)) {
            writer.write(GSON.toJson(promptInfo.getRecords()));
        } catch (IOException e) {
            throw new RuntimeException("In AbstractRunner.exportRecord: " + e);
        }
    }
        */
}