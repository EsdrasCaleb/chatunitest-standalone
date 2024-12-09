package ufrn.chattester;

import zju.cst.aces.dto.ClassInfo;
import zju.cst.aces.dto.MethodInfo;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.util.Counter;
import zju.cst.aces.util.TestClassMerger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ClassRunner extends AbstractRunner {
    public ClassInfo classInfo;
    public File infoDir;
    public int index;

    public ClassRunner(Config config, String fullClassName) throws IOException {
        super(config, fullClassName);
        infoDir = config.getParseOutput().resolve(fullClassName.replace(".", File.separator)).toFile();
        if (!infoDir.isDirectory()) {
            config.getLog().warn("Error: " + fullClassName + " no parsed info found");
        }
        File classInfoFile = new File(infoDir + File.separator + "class.json");
        classInfo = GSON.fromJson(Files.readString(classInfoFile.toPath(), StandardCharsets.UTF_8), ClassInfo.class);
    }

    @Override
    public void start() throws IOException {
        for (String mSig : classInfo.methodSigs.keySet()) {
            MethodInfo methodInfo = getMethodInfo(config, classInfo, mSig);
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
}
