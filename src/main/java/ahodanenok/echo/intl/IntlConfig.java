package ahodanenok.echo.intl;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;

@Profile("intl")
@Configuration
public class IntlConfig {

    @Bean(name = "echo")
    public IntlEcho createEcho() {
        return new IntlEcho();
    }

    @Bean(name = "messageSource")
    public MessageSource createMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ahodanenok/echo/intl/echo");
        return messageSource;
    }
}
