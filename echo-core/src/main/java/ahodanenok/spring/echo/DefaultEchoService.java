package ahodanenok.spring.echo;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class DefaultEchoService implements EchoService, EnvironmentAware {

    private final Echo impl;

    private Counter counter;
    private boolean persistentCounter;

    private Environment environment;
    private final FormattingConversionService conversionService;

    public DefaultEchoService(Echo impl) {
        this.impl = impl;

        this.conversionService = new DefaultFormattingConversionService();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public void setPersistentCounter(boolean persistentCounter) {
        this.persistentCounter = persistentCounter;
    }

    private void init() {
        if (counter != null && persistentCounter) {
            File file = getCounterPropertiesFile();
            if (!file.exists()) {
                return;
            }

            try {
                Properties counterProperties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(file));
                counter.setRequestCount(conversionService.convert(counterProperties.getProperty("counter.requests", "0"), Integer.class));
                counter.setErrorCount(conversionService.convert(counterProperties.getProperty("counter.errors", "0"), Integer.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void destroy() {
        if (counter != null && persistentCounter) {
            Properties props = new Properties();
            props.setProperty("counter.requests", conversionService.convert(counter.getRequestCount(), String.class));
            props.setProperty("counter.errors", conversionService.convert(counter.getErrorCount(), String.class));

            try {
                props.store(new FileSystemResource(getCounterPropertiesFile()).getOutputStream(), "counter state");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getCounterPropertiesFile() {
        File file = environment.getProperty("user.home", File.class,new File("."));
        return new File(file, "counter.properties");
    }

    @Override
    public String echo() {
        try {
            // todo: formatter service
            return impl.echo().toString();
        } catch (Exception e) {
            if (counter != null) counter.countError();
            e.printStackTrace();
            return null;
        } finally {
            if (counter != null) counter.countRequest();
        }
    }
}
