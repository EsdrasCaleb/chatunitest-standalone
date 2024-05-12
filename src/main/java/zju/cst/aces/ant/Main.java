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
    static String url = "http://127.0.0.1/v1/chat/completions";
    static String model = "code-llama";

    public static void main(String[] args) {
        // 检查是否传递了参数
        if (args.length == 0) {
            System.out.println("Usage: java -jar my-project.jar [envFilePath] [arg1] [arg2]...");
            return;
        }

        if (args[0].equals("createEnv")) {
            autoEnv4SF(args);
            return;
        }

        String envFilePath = args[0];
        // 传递其他参数
        String[] command = Arrays.copyOfRange(args, 1, args.length);
        new CommandHandler(envFilePath).handle(command);
    }

    static void autoEnv4SF(String[] args) {
        if (args.length != 7) {
            System.out.println("Usage: java -jar chatunitest-standalone.jar createEnv [srcPath] [compiledPath] [dependencyPath] [apiKey] [url] [model]");
            return;
        }
        srcPath = Paths.get(args[1]);
        compiledPath = Paths.get(args[2]);
        dependencyPath = Paths.get(args[3]);
        apiKey = args[4];
        url = args[5];
        model = args[6];

        if (!srcPath.toFile().exists() || !compiledPath.toFile().exists() || !dependencyPath.toFile().exists()) {
            System.out.println("srcPath, compiledPath or dependencyPath not exists");
            return;
        }

        for (File file : Objects.requireNonNull(srcPath.toFile().listFiles())) {
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
                    content.append("url=").append(url).append("\n");
                    content.append("model=").append(model).append("\n");
                    content.append("enableMultithreading=").append(true).append("\n");
                    content.append("parentEnvPath=\n");
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
        Path compiledProjectPath = compiledPath.resolve(srcDirPath.toFile().getName());
        try {
            File srcDir = compiledProjectPath.toFile();
            File destDir = srcDirPath.resolve("target").toFile();
            if (!destDir.exists() && !destDir.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + destDir);
            }
            for (File file : Objects.requireNonNull(srcDir.listFiles())) {
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