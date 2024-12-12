package zju.cst.aces.ant;

import zju.cst.aces.api.Task;
import zju.cst.aces.api.config.*;
import zju.cst.aces.api.impl.RunnerImpl;
import ufrn.chattester.TestRunnerImpl;
import ufrn.chattester.TestTask;

import java.nio.file.Paths;

public class CommandHandler {

    Config config;

    public CommandHandler(String envFilePath) {
        EnvReader envReader = new EnvReader(envFilePath);
        MyProject project = new MyProject(envReader);
        this.config = new Config.ConfigBuilder(project)
                .tmpOutput(Paths.get("/tmp/chatunitest-info"))
                .examplePath(Paths.get(envReader.getBasedir()+"/exampleUsage.json"))
                .apiKeys(envReader.getApiKeys())
                .url(envReader.getUrl())
                .model(envReader.getModel())
                .enableMultithreading(envReader.isEnableMultithreading())
                .classPaths(envReader.getClassPaths())
                .noExecution(envReader.getNoExecution())
                .build();
        config.print();
    }

    public void handle(String[] command) {
        switch (command[0]) {
            case "project":
                handleProjectCommand(command);
                break;
            case "class":
                handleClassCommand(command);
                break;
            case "method":
                handleMethodCommand(command);
                break;
            default:
                System.out.println("Unknown command: " + command[0]);
        }
    }

    private void handleProjectCommand(String[] args) {
        new TestTask(config, new TestRunnerImpl(config)).startProjectTask();
    }

    private void handleClassCommand(String[] args) {
        new TestTask(config, new TestRunnerImpl(config)).startClassTask(args[1]);
    }

    private void handleMethodCommand(String[] args) {
        new TestTask(config, new TestRunnerImpl(config)).startMethodTask(args[1], args[2]);
    }
}
