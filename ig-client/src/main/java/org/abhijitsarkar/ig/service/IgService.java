package org.abhijitsarkar.ig.service;

import org.abhijitsarkar.ig.domain.AccessToken;
import org.abhijitsarkar.ig.domain.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

/**
 * @author Abhijit Sarkar
 */
public class IgService {
    private final ExchangeFunction igClient = WebClient.create(new ReactorClientHttpConnector());

    @Value("${server.port:8080}")
    private int port;
    @Value("${HOST:}")
    private String host;
    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Autowired
    private IgProperties igProperties;

    private String authorizationUrl;
    private String redirectUri;

    @PostConstruct
    public void postConstruct() throws UnknownHostException {
        host = StringUtils.isEmpty(host) ? host() : host;
        redirectUri = String.format("%s/callback", host);
        authorizationUrl = UriComponentsBuilder.fromUriString(igProperties.getAuthorizeUrl())
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    private String host() throws UnknownHostException {
        return String.format("http://%s:%d", InetAddress.getLocalHost().getHostAddress(), port);
    }

    public final Mono<String> authorizationUrl() {
        return Mono.just(authorizationUrl);
    }

    public final Mono<Media> callback(String code) {
        return accessToken(code)
                .then(token -> top(recentPostsUrl(token)));
    }

    private Mono<AccessToken> accessToken(String code) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("client_id", clientId);
        queryParams.add("client_secret", clientSecret);
        queryParams.add("grant_type", "authorization_code");
        queryParams.add("redirect_uri", redirectUri);
        queryParams.add("code", code);

        return accessToken(igProperties.getAccessTokenUrl(), queryParams);
    }

    private String recentPostsUrl(AccessToken accessToken) {
        return String.format("%s?access_token=%s", igProperties.getRecentPostsUrl(), accessToken.getToken());
    }

    protected Mono<AccessToken> accessToken(String accessTokenUrl, MultiValueMap<String, String> queryParams) {
        ClientRequest<MultiValueMap<String, String>> request = ClientRequest.POST(accessTokenUrl)
                .contentType(APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromObject(queryParams));

        return igClient.exchange(request)
                .then(response -> response.bodyToMono(AccessToken.class));
    }

    protected Mono<Media> top(String recentPostsUrl) {
        ClientRequest<Void> request = ClientRequest.GET(recentPostsUrl)
                .build();

        return igClient.exchange(request)
                .then(response -> response.bodyToMono(Media.class));
    }
}
