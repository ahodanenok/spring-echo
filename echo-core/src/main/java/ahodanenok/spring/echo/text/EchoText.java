package ahodanenok.spring.echo.text;

import ahodanenok.spring.echo.Echo;

public class EchoText implements Echo {

    private final String message;
    private boolean upperCase;

    public EchoText(String message) {
        this.message = message;
    }

    public void setUpperCase(boolean upperCase) {
        this.upperCase = upperCase;
    }

    @Override
    public Object echo() {
        if (upperCase) {
            return message.toUpperCase();
        } else {
            return message;
        }
    }
}
