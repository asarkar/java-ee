package org.abhijitsarkar;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Collection;

import static org.springframework.http.HttpStatus.TEMPORARY_REDIRECT;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

/**
 * @author Abhijit Sarkar
 */
@Component
public class IgHandler {
    @Value("${HOST:}")
    private String host;
    @Value("${server.port:8080}")
    private int port;

    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    private WebClient igClient = WebClient.create(new ReactorClientHttpConnector());
    private String authorizationUrl;
    private String redirectUri;

    @PostConstruct
    public void postConstruct() throws UnknownHostException {
        host = StringUtils.isEmpty(host) ? host() : host;
        redirectUri = String.format("%s/callback", host);
        authorizationUrl = UriComponentsBuilder.fromUriString("https://api.instagram.com/oauth/authorize/")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    private String host() throws UnknownHostException {
        return String.format("http://%s:%d", InetAddress.getLocalHost().getHostAddress(), port);
    }

    Mono<ServerResponse> redirect() {
        return ServerResponse.status(TEMPORARY_REDIRECT)
                .location(URI.create(authorizationUrl))
                .build();
    }

    Mono<ServerResponse> callback(String code) {
        Publisher<Collection> pub = accessToken(code)
                .then(this::top);
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON_UTF8)
                .body(pub, Collection.class);
    }

    private Mono<AccessToken> accessToken(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "authorization_code");
        map.add("redirect_uri", redirectUri);
        map.add("code", code);

        ClientRequest<MultiValueMap<String, String>> request =
                ClientRequest.POST("https://api.instagram.com/oauth/access_token")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromObject(map));

        return igClient.exchange(request)
                .then(response -> response.bodyToMono(AccessToken.class));
    }

    private Mono<Collection> top(AccessToken token) {
        ClientRequest<Void> request =
                ClientRequest.GET("https://api.instagram.com/v1/users/self/media/recent?access_token={accessToken}", token.getToken())
                        .build();
        return igClient.exchange(request)
                .then(response -> response.bodyToMono(Collection.class));
    }
}
