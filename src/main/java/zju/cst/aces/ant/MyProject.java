package zju.cst.aces.ant;

import zju.cst.aces.api.Project;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class MyProject implements Project {

    EnvReader env;

    public MyProject(EnvReader env) {
        this.env = env;
    }

    @Override
    public Project getParent() {
        return env.getParent();
    }

    @Override
    public File getBasedir() {
        return env.getBasedir();
    }

    @Override
    public String getPackaging() {
        System.out.println("3");
        return env.getPackaging();
    }

    @Override
    public String getGroupId() {
        return env.getGroupId();
    }

    @Override
    public String getArtifactId() {
        return env.getArtifactId();
    }

    @Override
    public List<String> getCompileSourceRoots() {
        return env.getCompileSourceRoots();
    }

    @Override
    public Path getArtifactPath() {
        return env.getArtifactPath();
    }

    @Override
    public Path getBuildPath() {
        return env.getBuildPath();
    }


    public List<String> getClassPaths() {
        return env.getClassPaths();
    }
}
