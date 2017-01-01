package org.abhijitsarkar.ig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.abhijitsarkar.ig.domain.AccessToken;
import org.abhijitsarkar.ig.domain.Media;
import org.abhijitsarkar.ig.service.IgService;
import org.abhijitsarkar.ig.service.WebClientIgService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Abhijit Sarkar
 */
@Configuration
@Profile("test")
public class IgTestConfiguration {
    @Bean
    IgService webClientIgService() throws IOException {
        ExchangeFunction webClient = mock(ExchangeFunction.class);
        Mono<ClientResponse> response = Mono.just(clientResponse());
        when(webClient.exchange(any(ClientRequest.class))).thenReturn(response);

        return new WebClientIgService(webClient);
    }

    private ClientResponse clientResponse() throws IOException {
        ClientResponse response = null;

        try (InputStream is = getClass().getResourceAsStream("/recent.json")) {
            Media media = new ObjectMapper().reader().forType(Media.class).readValue(is);
            AccessToken token = new AccessToken();
            token.setToken("whatever");
            response = mock(ClientResponse.class);

            when(response.statusCode()).thenReturn(OK);
            when(response.bodyToMono(AccessToken.class)).thenReturn(Mono.just(token));
            when(response.bodyToMono(Media.class)).thenReturn(Mono.just(media));
        }

        return response;
    }
}
