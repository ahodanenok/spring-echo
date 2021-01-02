package ahodanenok.spring.echo;

public class DefaultEchoService implements EchoService {

    private Echo impl;

    public DefaultEchoService(Echo impl) {
        this.impl = impl;
    }

    @Override
    public String echo() {
        try {
            // todo: formatter service
            return impl.echo().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
