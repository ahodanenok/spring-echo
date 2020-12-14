package ahodanenok.echo.impl;

import ahodanenok.echo.Echo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SimpleEcho implements Echo, InitializingBean, DisposableBean {

    public String say() {
        return "echo";
    }

    @PostConstruct
    private void postConstructInit() {
        println("@PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        println("InitializingBean.afterPropertiesSet");
    }

    private void initFromXml() {
        println("init from xml");
    }

    private void destroyFromXml() {
        println("destroy from xml");
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
