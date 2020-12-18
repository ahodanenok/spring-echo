package ahodanenok.echo.intl;

import ahodanenok.echo.Echo;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import java.util.Locale;

public class IntlEcho implements Echo, MessageSourceAware {

    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String say() {
        return messageSource.getMessage("echo", null, Locale.ENGLISH);
    }
}
