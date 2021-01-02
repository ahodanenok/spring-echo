package ahodanenok.spring.echo.text;

import ahodanenok.spring.echo.Echo;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import java.util.Locale;

public class EchoIntl implements Echo, MessageSourceAware {

    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Object echo() {
        return messageSource.getMessage("message", new Object[0], Locale.getDefault());
    }
}
