package ahodanenok.echo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class EchoClient implements EnvironmentAware {

    private Echo echo;

    private boolean saveHistory;
    private Resource historyFile;

    public EchoClient(Echo echo) {
        this.echo = echo;
    }

    @Override
    public void setEnvironment(Environment environment) {
        saveHistory = environment.getProperty("echo.history.save", Boolean.class, false);
        if (saveHistory && environment.containsProperty("echo.history.file")) {
            String fileName = environment.getProperty("echo.history.file");
            if (fileName == null) {
                fileName = environment.getRequiredProperty("user.home") + "/echo.txt";
            }

            historyFile = new FileSystemResource(fileName);
        }
    }

    public String echo() {
        return echo.say();
    }
}
