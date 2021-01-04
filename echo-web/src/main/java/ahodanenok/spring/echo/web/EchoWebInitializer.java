package ahodanenok.spring.echo.web;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class EchoWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // todo: make configurable
        System.setProperty("spring.profiles.active", "pokemon");

        XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        rootContext.setConfigLocation("classpath:ahodanenok/spring/echo/context.xml");
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(EchoConfig.class);

        ServletRegistration.Dynamic dispatcherRegistration =
                servletContext.addServlet(AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME, new DispatcherServlet(dispatcherContext));
        dispatcherRegistration.setLoadOnStartup(1);
        dispatcherRegistration.addMapping("/");
    }
}
