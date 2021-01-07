package ahodanenok.spring.echo.faulty;

import ahodanenok.spring.echo.Echo;

public class EchoFaulty implements Echo {

    @Override
    public Object echo() {
        double r = Math.random();
        if (r < 0.3) {
            return "OK";
        } else if (r < 0.6) {
            throw new EchoFaultyException();
        } else {
            throw new EchoFaultyCriticalException();
        }
    }
}
