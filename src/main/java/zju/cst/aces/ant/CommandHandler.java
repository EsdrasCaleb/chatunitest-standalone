package zju.cst.aces.ant;

import zju.cst.aces.api.Project;
import zju.cst.aces.api.Task;
import zju.cst.aces.api.config.*;
import zju.cst.aces.api.impl.RunnerImpl;

import java.nio.file.Paths;

public class CommandHandler {

    Config config;

    public CommandHandler(String envFilePath) {
        EnvReader envReader = new EnvReader(envFilePath);
        MyProject project = new MyProject(envReader);
        this.config = new Config.ConfigBuilder(project)
                .tmpOutput(Paths.get("/tmp/chatunitest-info"))//Usar outro temp para o projeto no env
                .model(envReader.getModel())
                .apiKeys(envReader.getApiKeys())
                .url(envReader.getUrl())
                .pluginSign(envReader.getPlugin())
                .enableMultithreading(envReader.isEnableMultithreading())
                .classPaths(envReader.getClassPaths())
                .phaseType(envReader.getPhase())
                .timeOut(envReader.getTimeout())
                .maxResponseTokens(envReader.getTokens())
                .maxPromptTokens(envReader.getPromptTokens())
                .temperature(envReader.getTemperature())
                .useIntention(envReader.getUseIntention())
                .testOutput(envReader.getChatUnitpath())
                .benchMarkCsv(envReader.getBenchMarkCsv())
                .sleepTime(envReader.getSleeptime())
                .logFaultAssert(true)
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
            case "csv":
                ProcessMutationCsv processMuationCsv = new ProcessMutationCsv(config);
                processMuationCsv.makeNewCsv(command[1]);
                break;
            case "test":
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Cannot Sleep");
                }
                String commad = "";
                if(command.length > 1) {
                    commad = command[1];
                }
                System.out.println("Test ok "+commad);
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private void handleProjectCommand(String[] args) {
        try {
            new Task(config, new RunnerImpl(config)).startProjectTask();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClassCommand(String[] args) {
        try {
            new Task(config, new RunnerImpl(config)).startClassTask(args[1]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMethodCommand(String[] args) {
        try {
            new Task(config, new RunnerImpl(config)).startMethodTask(args[1], args[2]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
