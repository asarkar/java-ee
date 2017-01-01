package org.abhijitsarkar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


/**
 * @author Abhijit Sarkar
 */
@Controller
public class IgController {
    @Autowired
    private IgHandler handler;

    @GetMapping("/")
    public Mono<ServerResponse> redirect() {
        return handler.redirect();
    }

    @ResponseBody
    @GetMapping("/callback")
    public Mono<ServerResponse> callback(@RequestParam("code") String code) {
        return handler.callback(code);
    }
}
