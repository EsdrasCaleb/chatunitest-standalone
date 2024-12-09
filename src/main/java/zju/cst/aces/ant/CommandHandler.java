package zju.cst.aces.ant;

import zju.cst.aces.api.Task;
import zju.cst.aces.api.config.*;
import zju.cst.aces.api.impl.RunnerImpl;
import ufrn.chattester.TesterMethodRunner;

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
            default:
                System.out.println("Unknown command: " + command);
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
        try{
            TesterMethodRunner runner = new TesterMethodRunner(config,args[1], args[2]);
            runner.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
