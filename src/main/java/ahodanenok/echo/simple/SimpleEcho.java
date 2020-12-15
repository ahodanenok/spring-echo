package ahodanenok.echo.simple;

import ahodanenok.echo.Echo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SimpleEcho implements Echo, InitializingBean, DisposableBean {

    private String message = "echo";
    private Sequence sequence;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public String say() {
        String result = message;
        if (sequence != null) {
            result += " (" + sequence.next() + ")";
        }

        return result;
    }

    @PostConstruct
    private void postConstructInit() {
        println("@PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        println("InitializingBean.afterPropertiesSet");
    }


    @Override
    public void destroy() throws Exception {
        println("DisposableBean.destroy");
    }

    @PreDestroy
    public void preDestroy() {
        println("@PreDestroy");
    }

    private void println(String msg) {
        System.out.println(getClass().getName() + ": " + msg);
    }
}
