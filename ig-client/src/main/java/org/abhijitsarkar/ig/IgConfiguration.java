package org.abhijitsarkar.ig;

import org.abhijitsarkar.ig.service.IgService;
import org.abhijitsarkar.ig.service.RestOperationsIgService;
import org.abhijitsarkar.ig.service.WebClientIgService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Abhijit Sarkar
 */
@Configuration
@Profile("!test")
public class IgConfiguration {
    @Bean
    @Profile("!webClient")
    IgService restOperationsIgService() {
        return new RestOperationsIgService(new RestTemplate());
    }

    @Bean
    @Profile("webClient")
    IgService webClientIgService() {
        return new WebClientIgService(WebClient.create(new ReactorClientHttpConnector()));
    }
}
