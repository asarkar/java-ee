package org.abhijitsarkar.ig.service;

import lombok.RequiredArgsConstructor;
import org.abhijitsarkar.ig.domain.AccessToken;
import org.abhijitsarkar.ig.domain.Media;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class RestOperationsIgService extends AbstractIgService {
    private final RestOperations restTemplate;

    @Override
    protected Mono<AccessToken> accessToken(MultiValueMap<String, String> queryParams) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(queryParams, headers);

        return Mono.just(restTemplate)
                .map(rt -> rt.postForEntity(
                        "https://api.instagram.com/oauth/access_token", request, AccessToken.class))
                .map(ResponseEntity::getBody);
    }

    @Override
    protected Mono<Collection> top(AccessToken accessToken) {
        return Mono.just(restTemplate)
                .map(rt -> rt.getForObject(
                        "https://api.instagram.com/v1/users/self/media/recent?access_token={accessToken}",
                        Media.class,
                        accessToken.getToken()
                ))
                .map(Media::getMedia);
    }
}
