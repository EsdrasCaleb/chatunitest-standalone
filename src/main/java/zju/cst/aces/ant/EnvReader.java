package zju.cst.aces.ant;

import zju.cst.aces.api.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import zju.cst.aces.api.config.Model;


public class EnvReader {

    Properties properties;

    public EnvReader(String envFilePath) {
        loadEnv(envFilePath);
    }

    public void loadEnv(String filePath) {
        this.properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load env file: " + filePath, e);
        }
    }


    /**
     * Get Configurations
     */

    public String[] getApiKeys() {
        return this.properties.getProperty("apiKeys").isEmpty() ? new String[0] : this.properties.getProperty("apiKeys").split(",");
    }

    public String getUrl() {
        return this.properties.getProperty("url").isEmpty() ? "https://api.openai.com/v1/chat/completions" : this.properties.getProperty("url");
    }

    public String getModel() {
        return this.properties.getProperty("model").isEmpty() ? "gpt-3.5-turbo" : this.properties.getProperty("model");
    }

    public boolean isEnableMultithreading() {
        return this.properties.getProperty("enableMultithreading").isEmpty() ? true : Boolean.parseBoolean(this.properties.getProperty("enableMultithreading"));
    }


    /**
     * Get Project Information
     */

    public Project getParent() {
        return this.properties.getProperty("parentEnvPath").isEmpty() ? null : new MyProject(new EnvReader(this.properties.getProperty("parentEnvPath")));
    }

    public File getBasedir() {
        return new File(this.properties.getProperty("baseDir"));
    }

    public String getPackaging() {
        return this.properties.getProperty("packaging").isEmpty() ? "jar" : this.properties.getProperty("packaging");
    }

    public String getGroupId() {
        return this.properties.getProperty("groupId");
    }

    public String getArtifactId() {
        return this.properties.getProperty("artifactId");
    }

    public List<String> getCompileSourceRoots() {
        return Collections.singletonList(this.properties.getProperty("compileSourceRoots"));
    }

    public Path getArtifactPath() {
        return Path.of(this.properties.getProperty("artifactPath"));
    }

    public Path getBuildPath() {
        return Path.of(this.properties.getProperty("buildPath"));
    }
    public Path getChatUnitpath() {
        System.out.println("Testpath:"+this.properties.getProperty("chatunitest-tests"));
        return Path.of(this.properties.getProperty("chatunitest-tests"));
    }
    public String getPhase(){ return this.properties.getProperty("phase"); }
    public String getPlugin(){return this.properties.getProperty("plugin");}
    public String getBenchMarkCsv(){return this.properties.getProperty("benchmark_file");}

    public int getTimeout(){
        if(this.properties.containsKey("timeout")&&!this.properties.getProperty("timeout").isEmpty()){
            return Integer.parseInt(this.properties.getProperty("timeout"));
        }
        return 5;
    }
    public int getSleeptime(){
        if(this.properties.containsKey("sleeptime")&&!this.properties.getProperty("sleeptime").isEmpty()){
            return Integer.parseInt(this.properties.getProperty("sleeptime"));
        }
        return 3000;
    }
    public int getTokens(){
        if(this.properties.containsKey("max_tokens")&&!this.properties.getProperty("max_tokens").isEmpty()){
            return Integer.parseInt(this.properties.getProperty("max_tokens"));
        }
        return 1024;
    }
    public int getPromptTokens(){
        if(this.properties.containsKey("max_prompt_tokens")&&!this.properties.getProperty("max_prompt_tokens").isEmpty()){
            return Integer.parseInt(this.properties.getProperty("max_prompt_tokens"));
        }
        return 2600;
    }
    public double getTemperature(){
        if(this.properties.containsKey("temperature")&&!this.properties.getProperty("temperature").isEmpty()){
            return Double.parseDouble(this.properties.getProperty("temperature"));
        }
        return 0.5;
    }
    public boolean getUseIntention(){
        if(this.properties.containsKey("use_intention")&&!this.properties.getProperty("use_intention").isEmpty()){
            return Boolean.parseBoolean(this.properties.getProperty("use_intention"));
        }
        return true;
    }
    public List<String> getClassPaths() {
        List<String> classPaths = new java.util.ArrayList<>(Collections.emptyList());
        String[] cp = this.properties.getProperty("classPaths").split(":");
        for (String dep : cp) {
            File f = new File(dep);
            if (f.isDirectory()) {
                try {
                    Files.walk(f.toPath()).forEach(path -> {
                        if (path.toString().endsWith(".jar")) {
                            classPaths.add(path.toString());
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (f.isFile() && f.getName().endsWith(".jar")) {
                classPaths.add(dep);
            }
        }
        return classPaths;
    }
}
