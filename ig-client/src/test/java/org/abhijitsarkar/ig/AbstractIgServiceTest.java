package org.abhijitsarkar.ig;

import org.abhijitsarkar.ig.domain.Media;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

/**
 * @author Abhijit Sarkar
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = DEFINED_PORT
)
public abstract class AbstractIgServiceTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCallback() {
        Media response = restTemplate.getForObject("/callback?code={code}", Media.class, "whatever");
        assertThat(response).isNotNull();

        Collection<Media.Medium> media = response.getMedia();
        assertThat(media).hasSize(1);

        Media.Medium medium = media.iterator().next();
        assertThat(medium).isNotNull();
        assertThat(medium.getLink()).isEqualTo("http://whatever");
        assertThat(medium.getLikes().getCount()).isEqualTo(1);
    }
}
