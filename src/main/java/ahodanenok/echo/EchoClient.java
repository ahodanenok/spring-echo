package ahodanenok.echo;

public class EchoClient {

    private Echo echo;

    public EchoClient(Echo echo) {
        this.echo = echo;
    }

    public String echo() {
        return echo.say();
    }
}
