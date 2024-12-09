package ufrn.chattester;

import zju.cst.aces.api.Project;
import zju.cst.aces.api.Runner;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.dto.ClassInfo;
import zju.cst.aces.dto.MethodInfo;
import zju.cst.aces.parser.ProjectParser;
import zju.cst.aces.runner.AbstractRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import zju.cst.aces.api.Logger;
import zju.cst.aces.util.Counter;

public class TestTask {

    Config config;
    Logger log;
    Runner runner;

    public TestTask(Config config, Runner runner) {
        this.config = config;
        this.log = config.getLog();
        this.runner = runner;
    }

    public void startMethodTask(String className, String methodName) {
        try {
            checkTargetFolder(config.getProject());
        } catch (RuntimeException e) {
            log.error(e.toString());
            return;
        }
        if (config.getProject().getPackaging().equals("pom")) {
            log.info("\n==========================\n[ChatUniTest] Skip pom-packaging ...");
            return;
        }
        ProjectParser parser = new ProjectParser(config);
        parser.parse();
        log.info("\n==========================\n[ChatUniTest] Generating tests for class: < " + className
                + "> method: < " + methodName + " > ...");

        try {
            String fullClassName = getFullClassName(config, className);
            ClassInfo classInfo = AbstractRunner.getClassInfo(config, fullClassName);
            MethodInfo methodInfo = null;
            if (methodName.matches("\\d+")) { // use method id instead of method name
                String methodId = methodName;
                for (String mSig : classInfo.methodSigs.keySet()) {
                    if (classInfo.methodSigs.get(mSig).equals(methodId)) {
                        methodInfo = AbstractRunner.getMethodInfo(config, classInfo, mSig);
                        break;
                    }
                }
                if (methodInfo == null) {
                    throw new IOException("Method " + methodName + " in class " + fullClassName + " not found");
                }
                try {
                    this.runner.runMethod(fullClassName, methodInfo);
                } catch (Exception e) {
                    log.error("Error when generating tests for " + methodName + " in " + className + " " + config.getProject().getArtifactId() + "\n" + e.getMessage());
                }
            } else {
                log.warn("1b");
                log.warn(methodName);
                for (String mSig : classInfo.methodSigs.keySet()) {
                    if (mSig.split("\\(")[0].equals(methodName)) {
                        log.warn("signal:"+mSig);
                        methodInfo = AbstractRunner.getMethodInfo(config, classInfo, mSig);
                        if (methodInfo == null) {
                            throw new IOException("Method " + methodName + " in class " + fullClassName + " not found");
                        }
                        try {
                            this.runner.runMethod(fullClassName, methodInfo);
                        } catch (Exception e) {
                            log.error("Error when generating tests for " + methodName + " in " + className + " " + config.getProject().getArtifactId() + "\n" + e.getMessage());
                        }
                    }
                }
            }

        } catch (IOException e) {
            log.warn(e.getMessage());
            log.warn("Method not found: " + methodName + " in " + className + " " + config.getProject().getArtifactId());
            return;
        }

        log.info("\n==========================\n[ChatUniTest] Generation finished");
    }

    public void startClassTask(String className) {
        try {
            checkTargetFolder(config.getProject());
        } catch (RuntimeException e) {
            log.error(e.toString());
            return;
        }
        if (config.getProject().getPackaging().equals("pom")) {
            log.info("\n==========================\n[ChatUniTest] Skip pom-packaging ...");
            return;
        }
        ProjectParser parser = new ProjectParser(config);
        parser.parse();
        log.info("\n==========================\n[ChatUniTest] Generating tests for class < " + className + " > ...");
        try {
            this.runner.runClass(getFullClassName(config, className));
        } catch (IOException e) {
            log.warn("Class not found: " + className + " in " + config.getProject().getArtifactId());
        }
        log.info("\n==========================\n[ChatUniTest] Generation finished");
    }

    public void startProjectTask() {
        Project project = config.getProject();
        try {
            checkTargetFolder(project);
        } catch (RuntimeException e) {
            log.error(e.toString());
            return;
        }
        if (project.getPackaging().equals("pom")) {
            log.info("\n==========================\n[ChatUniTest] Skip pom-packaging ...");
            return;
        }
        ProjectParser parser = new ProjectParser(config);
        parser.parse();
        List<String> classPaths = ProjectParser.scanSourceDirectory(project);
        
        for (String classPath : classPaths) {
            String className = classPath.substring(classPath.lastIndexOf(File.separator) + 1, classPath.lastIndexOf("."));
            try {
                String fullClassName = getFullClassName(config, className);
                log.info("\n==========================\n[ChatUniTest] Generating tests for class < " + className + " > ...");
                log.info(fullClassName);
                ClassInfo info = AbstractRunner.getClassInfo(config, fullClassName);
                if (info == null || !Counter.filter(info)) {
                    config.getLog().info("Skip class: " + classPath);
                    continue;
                }
                this.runner.runClass(fullClassName);
            } catch (IOException e) {
                log.error("[ChatUniTest] Generate tests for class " + className + " failed: " + e);
            }  
        }

        log.info("\n==========================\n[ChatUniTest] Generation finished");
    }

    public void projectJob(List<String> classPaths) {
        ExecutorService executor = Executors.newFixedThreadPool(config.getClassThreads());
        List<Future<String>> futures = new ArrayList<>();
        for (String classPath : classPaths) {
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String className = classPath.substring(classPath.lastIndexOf(File.separator) + 1, classPath.lastIndexOf("."));
                    try {
                        String fullClassName = getFullClassName(config, className);
                        log.info("\n==========================\n[ChatUniTest] Generating tests for class < " + className + " > ...");
                        ClassInfo info = AbstractRunner.getClassInfo(config, fullClassName);
                        if (!Counter.filter(info)) {
                            return "Skip class: " + classPath;
                        }
                        runner.runClass(fullClassName);
                    } catch (IOException e) {
                        log.error("[ChatUniTest] Generate tests for class " + className + " failed: " + e);
                    }
                    return "Processed " + classPath;
                }
            };
            Future<String> future = executor.submit(callable);
            futures.add(future);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                executor.shutdownNow();
            }
        });

        for (Future<String> future : futures) {
            try {
                String result = future.get();
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }

    public static String getFullClassName(Config config, String name) throws IOException {
        if (isFullName(name)) {
            return name;
        }
        Path classMapPath = config.getClassNameMapPath();
        Map<String, List<String>> classMap = config.getGSON().fromJson(Files.readString(classMapPath, StandardCharsets.UTF_8), Map.class);
        if (classMap.containsKey(name)) {
            if (classMap.get(name).size() > 1) {
                throw new RuntimeException("[ChatUniTest] Multiple classes Named " + name + ": " + classMap.get(name)
                        + " Please use full qualified name!");
            }
            return classMap.get(name).get(0);
        }
        return name;
    }

    public static boolean isFullName(String name) {
        if (name.contains(".")) {
            return true;
        }
        return false;
    }

    /**
     * Check if the classes is compiled
     * @param project
     */
    public static void checkTargetFolder(Project project) {
        if (project.getPackaging().equals("pom")) {
            return;
        }
        if (!new File(project.getBuildPath().toString()).exists()) {
            throw new RuntimeException("In ProjectTestMojo.checkTargetFolder: " +
                    "The project is not compiled to the target directory. " +
                    "Please run 'mvn install' first.");
        }
    }
}
