package ahodanenok.spring.echo.web.controller;

import ahodanenok.spring.echo.EchoService;
import ahodanenok.spring.echo.faulty.EchoFaultyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class EchoController {

    @Autowired
    private EchoService echoService;

    @GetMapping("/echo-1")
    public String echo1() throws Exception {
        return echoService.echo();
    }

    @GetMapping("/echo-2")
    public String echo2() throws Exception {
        return echoService.echo();
    }

    @ExceptionHandler(value = { EchoFaultyException.class })
    public void handleException(EchoFaultyException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ERROR!");
    }
}
