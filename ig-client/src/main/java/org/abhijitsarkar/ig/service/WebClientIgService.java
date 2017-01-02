package org.abhijitsarkar.ig.service;

import lombok.RequiredArgsConstructor;
import org.abhijitsarkar.ig.domain.AccessToken;
import org.abhijitsarkar.ig.domain.Media;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class WebClientIgService extends AbstractIgService {
    private final ExchangeFunction igClient;

    @Override
    protected Mono<AccessToken> accessToken(String accessTokenUrl, MultiValueMap<String, String> queryParams) {
        ClientRequest<MultiValueMap<String, String>> request = ClientRequest.POST(accessTokenUrl)
                .contentType(APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromObject(queryParams));

        return igClient.exchange(request)
                .then(response -> response.bodyToMono(AccessToken.class));
    }

    @Override
    protected Mono<Media> top(String recentPostsUrl) {
        ClientRequest<Void> request = ClientRequest.GET(recentPostsUrl)
                .build();

        return igClient.exchange(request)
                .then(response -> response.bodyToMono(Media.class));
    }
}
