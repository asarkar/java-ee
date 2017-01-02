package org.abhijitsarkar.ig.web;

import org.abhijitsarkar.ig.domain.Media;
import org.abhijitsarkar.ig.service.IgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


/**
 * @author Abhijit Sarkar
 */
@Controller
public class IgController {
    @Autowired
    private IgService igService;

    @GetMapping("/")
    public Mono<String> redirect() {
        return igService.authorizationUrl().map(url -> "redirect:" + url);
    }

    @ResponseBody
    @GetMapping(value = "/callback", produces = APPLICATION_JSON_UTF8_VALUE)
    public Mono<Media> callback(@RequestParam("code") String code) {
        return igService.callback(code);
    }
}
