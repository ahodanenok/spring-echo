package ahodanenok.spring.echo.cli;

import ahodanenok.spring.echo.EchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "intl");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ahodanenok/spring/echo/context.xml");
        EchoService echoService = context.getBean("echoService", EchoService.class);
        System.out.println(echoService.echo());
    }
}
