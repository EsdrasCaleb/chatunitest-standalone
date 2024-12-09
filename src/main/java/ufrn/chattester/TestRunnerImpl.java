package ufrn.chattester;

import zju.cst.aces.api.Runner;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.dto.ClassInfo;
import zju.cst.aces.dto.MethodInfo;
import zju.cst.aces.dto.PromptInfo;
import zju.cst.aces.dto.RoundRecord;
import zju.cst.aces.runner.AbstractRunner;
import zju.cst.aces.runner.ClassRunner;
import zju.cst.aces.runner.MethodRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class TestRunnerImpl implements Runner {
    Config config;

    public TestRunnerImpl(Config config) {
        this.config = config;
    }

    public void runClass(String fullClassName) {
        try {
            new TestClssRunner(config, fullClassName).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void runMethod(String fullClassName, MethodInfo methodInfo) {
        System.out.println("Class command: " +fullClassName);
        try {
            new TesterMethodRunner(config, fullClassName, methodInfo).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
