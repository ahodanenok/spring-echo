package ahodanenok.spring.echo.web.controller;

import ahodanenok.spring.echo.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @Autowired
    private EchoService echoService;

    @GetMapping
    public String echo() {
        return echoService.echo();
    }
}
