package ahodanenok.spring.echo.web.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EchoInterceptor implements HandlerInterceptor {

    public static final String REQUEST_TIMESTAMP_ATTRIBUTE = "echo.request.timestamp";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(REQUEST_TIMESTAMP_ATTRIBUTE, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle: " + (System.currentTimeMillis() - (Long) request.getAttribute(REQUEST_TIMESTAMP_ATTRIBUTE)));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion: " + (System.currentTimeMillis() - (Long) request.getAttribute(REQUEST_TIMESTAMP_ATTRIBUTE)));
    }
}
