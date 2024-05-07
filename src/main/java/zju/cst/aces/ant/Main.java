package zju.cst.aces.ant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    static Path srcPath = Paths.get("/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src");
    static Path compiledPath = Paths.get("/Users/chenyi/Downloads/sf110-chatgpt/SF110-20130704");
    static Path dependencyPath = Paths.get("/Users/chenyi/Desktop/ChatUniTest/chatunitest-standalone/src/main/resources/dependency");
    static String apiKey = "xxx";

    public static void main(String[] args) {
        // 检查是否传递了参数
        autoEnv4SF();

        if (args.length == 0) {
            System.out.println("Usage: java -jar my-project.jar [envFilePath] [arg1] [arg2]...");
            return;
        }

        String envFilePath = args[0];
        // 传递其他参数
        String[] command = Arrays.copyOfRange(args, 1, args.length);
        new CommandHandler(envFilePath).handle(command);
    }

    static void autoEnv4SF() {
        for (File file : srcPath.toFile().listFiles()) {
            if (file.isDirectory()) {
                if (!file.getName().contains("_")) {
                    continue;
                }
                String artifactId = file.getName().split("_")[1];
                copyCompiledFiles(file.toPath(), artifactId);

                Path envFilePath = file.toPath().resolve("chatunitest.env");
                StringBuilder content = new StringBuilder();
                // write content to envFilePath
                try {
                    Path absProjectPath = file.toPath().toAbsolutePath();
                    content.append("apiKeys=").append(apiKey).append("\n");
                    content.append("url=\n");
                    content.append("model=\n");
                    content.append("enableMultithreading=").append(true).append("\n");
                    content.append("parentEnvPath=\n");
                    content.append("classPaths=").append(artifactId).append("\n");
                    content.append("baseDir=").append(absProjectPath).append("\n");
                    content.append("packaging=\n");
                    content.append("groupId=").append("com.example").append("\n");
                    content.append("artifactId=").append(artifactId).append("\n");
                    content.append("compileSourceRoots=").append(absProjectPath.resolve("src").resolve("main").resolve("java")).append("\n");
                    String buildPath = absProjectPath.resolve("target").toString();
                    content.append("buildPath=").append(buildPath).append("\n");
                    content.append("artifactPath=").append(absProjectPath.resolve(artifactId + ".jar")).append("\n");
                    content.append("classPaths=")
                            .append(absProjectPath).append(":")
                            .append(absProjectPath.resolve("lib")).append(":")
                            .append(absProjectPath.resolve("test-lib")).append(":")
                            .append(buildPath).append(":")
                            .append(dependencyPath).append("\n");
                    FileUtils.writeStringToFile(envFilePath.toFile(), content.toString(), "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void copyCompiledFiles(Path srcDirPath, String artifactId) {
        // copy compiled files
        Path compiledProjectPath = compiledPath.resolve(artifactId);
        try {
            File srcDir = compiledProjectPath.toFile();
            File destDir = srcDirPath.resolve("target").toFile();
            for (File file : srcDir.listFiles()) {
                if (file.isDirectory() && (file.getName().equals("target") || file.getName().equals("build"))) {
                    FileUtils.copyDirectory(file, destDir);
                } else if (file.isFile() && file.getName().equals(artifactId + ".jar")) {
                    FileUtils.copyFileToDirectory(file, destDir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}