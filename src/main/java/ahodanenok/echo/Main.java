package ahodanenok.echo;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int echoCount = 5;

        ApplicationContext context = createContext();

        EchoClient client = context.getBean("client", EchoClient.class);
        for (int i = 0; i < echoCount; i++) {
            System.out.println(client.echo());
        }
    }

    private static ApplicationContext createContext() {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();

        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("ahodanenok/echo/echo.properties"));
        context.addBeanFactoryPostProcessor(placeholderConfigurer);

        context.getBeanFactory().addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
        context.getBeanFactory().addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

        try {
            context.getEnvironment().getPropertySources().addFirst(new ResourcePropertySource("ahodanenok/echo/env.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.registerShutdownHook();
        context.load("classpath:ahodanenok/echo/context.xml");
        context.refresh();

        return context;
    }
}
