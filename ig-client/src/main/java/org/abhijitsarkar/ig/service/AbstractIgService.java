package org.abhijitsarkar.ig.service;

import org.abhijitsarkar.ig.domain.AccessToken;
import org.abhijitsarkar.ig.domain.Media;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public final Mono<String> authorizationUrl() {
        return Mono.just(authorizationUrl);
    }

    @Override
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

    abstract protected Mono<AccessToken> accessToken(String accessTokenUrl, MultiValueMap<String, String> queryParams);

    abstract protected Mono<Media> top(String recentUrl);
}
