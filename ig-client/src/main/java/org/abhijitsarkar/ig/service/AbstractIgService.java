package org.abhijitsarkar.ig.service;

import org.abhijitsarkar.ig.domain.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
public abstract class AbstractIgService implements IgService {
    @Value("${HOST:}")
    private String host;
    @Value("${server.port:8080}")
    private int port;

    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

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

    @Override
    public Mono<String> authorizationUrl() {
        return Mono.just(authorizationUrl);
    }

    @Override
    public Mono<Collection> callback(String code) {
        return accessToken(code)
                .then(this::top);
    }

    private Mono<AccessToken> accessToken(String code) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("client_id", clientId);
        queryParams.add("client_secret", clientSecret);
        queryParams.add("grant_type", "authorization_code");
        queryParams.add("redirect_uri", redirectUri);
        queryParams.add("code", code);

        return accessToken(queryParams);
    }

    abstract protected Mono<AccessToken> accessToken(MultiValueMap<String, String> queryParams);

    abstract protected Mono<Collection> top(AccessToken accessToken);
}
