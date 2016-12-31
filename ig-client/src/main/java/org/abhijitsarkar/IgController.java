package org.abhijitsarkar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

/**
 * @author Abhijit Sarkar
 */
@RestController
public class IgController {
    @Value("${HOST:#{T(org.abhijitsarkar.IgController).host()}}")
    private String host;

    @Value("${CLIENT_ID}")
    private String clientId;
    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    private final RestOperations restTemplate = new RestTemplate();
    private final ObjectReader reader = new ObjectMapper().readerFor(IgResponse.class);
    private String authorizationUrl;
    private String redirectUri;

    public static String host() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    @PostConstruct
    public void postConstruct() {
        redirectUri = String.format("http://%s:8080/callback", host);
        authorizationUrl = UriComponentsBuilder.fromUriString("https://api.instagram.com/oauth/authorize/")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    @GetMapping("/url")
    public String url() {
        return authorizationUrl;
    }

    @GetMapping("/callback")
    public String accessToken(@RequestParam("code") String code) throws UnknownHostException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", "authorization_code");
        map.add("redirect_uri", redirectUri);
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.instagram.com/oauth/access_token", request, String.class);

        return response.getBody();
    }

    @GetMapping("/top")
    public Collection<Media> top(@RequestParam("accessToken") String accessToken) throws IOException {
        String response = restTemplate.getForObject("https://api.instagram.com/v1/users/self/media/recent?access_token={accessToken}", String.class, accessToken);

        IgResponse igResponse = reader.readValue(response);

        return igResponse.getMedia()
                .stream()
                .sorted(comparing(Media::getLikes).reversed())
                .collect(toList());
    }
}
