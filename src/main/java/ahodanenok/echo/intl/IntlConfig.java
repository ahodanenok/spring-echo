package ahodanenok.echo.intl;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class IntlConfig {

    @Bean(name = "intlEcho")
    public IntlEcho createEcho() {
        return new IntlEcho();
    }

    public MessageSource createMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("intl/echo");
        return messageSource;
    }
}
