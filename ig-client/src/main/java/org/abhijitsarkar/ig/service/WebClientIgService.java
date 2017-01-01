package org.abhijitsarkar.ig.service;

import lombok.RequiredArgsConstructor;
import org.abhijitsarkar.ig.domain.AccessToken;
import org.abhijitsarkar.ig.domain.Media;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class WebClientIgService extends AbstractIgService {
    private final ExchangeFunction igClient;

    @Override
    protected Mono<AccessToken> accessToken(MultiValueMap<String, String> queryParams) {
        ClientRequest<MultiValueMap<String, String>> request = ClientRequest.POST("https://api.instagram.com/oauth/access_token")
                .contentType(APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromObject(queryParams));

        return igClient.exchange(request)
                .then(response -> response.bodyToMono(AccessToken.class));
    }

    @Override
    protected Mono<Collection> top(AccessToken accessToken) {
        ClientRequest<Void> request =
                ClientRequest.GET("https://api.instagram.com/v1/users/self/media/recent?access_token={accessToken}", accessToken.getToken())
                        .build();

        return igClient.exchange(request)
                .then(response -> response.bodyToMono(Media.class))
                .map(Media::getMedia);
    }
}
