package ahodanenok.echo;

import ahodanenok.echo.intl.IntlConfig;
import ahodanenok.echo.pokemon.PokemonConfig;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int echoCount = 5;

        System.setProperty("spring.profiles.active", "pokemon");
        ConfigurableApplicationContext context = createClientContext(createSimpleContext(createIntlContext(createPokemonContext(null))));

        EchoClient client = context.getBean("client", EchoClient.class);
        for (int i = 0; i < echoCount; i++) {
            System.out.println(client.echo());
        }
    }

    private static ConfigurableApplicationContext createClientContext(ApplicationContext parent) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.setParent(parent);
        new XmlBeanDefinitionReader(context).loadBeanDefinitions("ahodanenok/echo/client/context.xml");
        context.registerShutdownHook();
        context.refresh();

        return context;
    }

    private static ConfigurableApplicationContext createSimpleContext(ApplicationContext parent) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setParent(parent);

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
        context.load("classpath:ahodanenok/echo/simple/context.xml");
        context.refresh();

        return context;
    }

    private static ConfigurableApplicationContext createIntlContext(ApplicationContext parent) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setParent(parent);
        context.register(IntlConfig.class);
        context.registerShutdownHook();
        context.refresh();

        return context;
    }

    private static ConfigurableApplicationContext createPokemonContext(ApplicationContext parent) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setParent(parent);
        context.register(PokemonConfig.class);
        context.registerShutdownHook();
        context.refresh();

        return context;
    }
}
