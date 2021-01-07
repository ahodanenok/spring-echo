package ahodanenok.spring.echo.web.controller;

import ahodanenok.spring.echo.faulty.EchoFaultyCriticalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EchoControllerAdvice {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "BAD REQUEST")
    @ExceptionHandler({EchoFaultyCriticalException.class})
    public String handle(EchoFaultyCriticalException e) {
        return "CRITICAL ERROR";
    }
}
