package org.abhijitsarkar.ig;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.abhijitsarkar.ig.domain.Media;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * @author Abhijit Sarkar
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = DEFINED_PORT,
        properties = {"server.port=8080", "CLIENT_ID=whatever", "CLIENT_SECRET=whatever"}
)
@ActiveProfiles("test")
public class IgApplicationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCallback() throws IOException {
        ParameterizedTypeReference<Collection<Media.Medium>> typeReference = new ParameterizedTypeReference<Collection<Media.Medium>>() {
        };

        String raw = restTemplate.getForObject("/callback?code={code}", String.class, "whatever");

        DocumentContext context = JsonPath.parse(raw);
        int len = context.read("$.length()");
        assertThat(len).isEqualTo(1);

        String link = context.read("$[0].link");
        int likes = context.read("$[0].likes");

        assertThat(link).isEqualTo("https://www.instagram.com/p/BLaDxBQlAOL/");
        assertThat(likes).isEqualTo(21);
    }
}
