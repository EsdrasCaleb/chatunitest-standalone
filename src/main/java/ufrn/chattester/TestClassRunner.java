package ufrn.chattester;

import zju.cst.aces.dto.ClassInfo;
import zju.cst.aces.dto.MethodInfo;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.util.Counter;
import zju.cst.aces.util.TestClassMerger;
import zju.cst.aces.runner.ClassRunner;
import zju.cst.aces.parser.ClassParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestClassRunner extends ClassRunner {
    public ClassInfo classInfo;
    public File infoDir;
    public int index;

    public TestClassRunner(Config config, String fullClassName) throws IOException {
        super(config, fullClassName);
        infoDir = config.getParseOutput().resolve(fullClassName.replace(".", File.separator)).toFile();
        System.out.println("Info:"+infoDir.toString());
        if (!infoDir.isDirectory()) {
            config.getLog().warn("Error: " + fullClassName + " no parsed info found");
        }
        File classInfoFile = new File(infoDir + File.separator + "class.json");
        classInfo = GSON.fromJson(Files.readString(classInfoFile.toPath(), StandardCharsets.UTF_8), ClassInfo.class);
    }

    @Override
    public void start() throws IOException {
        for (String mSig : classInfo.methodSigs.keySet()) {
            MethodInfo methodInfo = getMethodInfoD(config, classInfo, mSig);
            if (!Counter.filter(methodInfo)) {
                config.getLog().info("Skip method: " + mSig + " in class: " + fullClassName);
                continue;
            }
            new TesterMethodRunner(config, fullClassName, methodInfo).start();
        }
        if (config.isEnableMerge()) {
            new TestClassMerger(config, fullClassName).mergeWithSuite();
        }
    }
    
    public static MethodInfo getMethodInfoD(Config config, ClassInfo info, String mSig) throws IOException {
        String packagePath = info.getPackageName()
                .replace("package ", "")
                .replace(".", File.separator)
                .replace(";", "");
        config.getLog().warn(packagePath);
        Path depMethodInfoPath = config.getParseOutput()
                .resolve(packagePath)
                .resolve(info.className)
                .resolve(ClassParser.getFilePathBySig(mSig, info));
        Path buggedPathdepMethodInfoPath = config.getParseOutput()
                .resolve(info.className)
                .resolve(ClassParser.getFilePathBySig(mSig, info));
        config.getLog().warn("Path:"+depMethodInfoPath.toString());
        config.getLog().warn("Path:"+buggedPathdepMethodInfoPath.toString());
        if (!depMethodInfoPath.toFile().exists()) {
            if(buggedPathdepMethodInfoPath.toFile().exists()){
                Path packageDir = config.getParseOutput().resolve(packagePath).resolve(info.className).normalize();
                if (!Files.exists(packageDir)) {
                    Files.createDirectories(packageDir);
                }

                // Copy files from sourcePath to targetPath
                Files.walkFileTree(config.getParseOutput().resolve(info.className), new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Path targetFile = packageDir.resolve(file.getFileName());
                        config.getLog().warn(targetFile.toString());
                        Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                        return FileVisitResult.CONTINUE;
                    }
                });
                config.getLog().warn("Copyed");
                depMethodInfoPath = config.getParseOutput()
                .resolve(packagePath)
                .resolve(info.className)
                .resolve(ClassParser.getFilePathBySig(mSig, info));
            }
            else{
                return null;
            }
        }
        return GSON.fromJson(Files.readString(depMethodInfoPath, StandardCharsets.UTF_8), MethodInfo.class);
    }
}
