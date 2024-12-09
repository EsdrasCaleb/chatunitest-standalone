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
                .apiKeys(envReader.getApiKeys())
                .url(envReader.getUrl())
                .model(envReader.getModel())
                .enableMultithreading(envReader.isEnableMultithreading())
                .classPaths(envReader.getClassPaths())
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
        System.out.println("Project command: " +args.toString());
        //new Task(config, new RunnerImpl(config)).startProjectTask();
    }

    private void handleClassCommand(String[] args) {
        System.out.println("Class command: " +args.toString());
        //new Task(config, new RunnerImpl(config)).startClassTask(args[1]);
    }

    private void handleMethodCommand(String[] args) {
        new TestTask(config, new TestRunnerImpl(config)).startMethodTask(args[1], args[2]);
    }
}
