package name.abhijitsarkar.javaee.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import name.abhijitsarkar.javaee.common.ObjectMapperFactory;
import name.abhijitsarkar.javaee.news.domain.TopStories;
import name.abhijitsarkar.javaee.news.repository.NYTClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.io.InputStream;

import static java.util.Collections.emptyList;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class NYTMockClient implements NYTClient {
    private ObjectMapper objectMapper = ObjectMapperFactory.newInstance();

    @Override
    public TopStories getTopStories(@PathVariable("section") String section) {
        TopStories topStories = null;

        try (InputStream is = getClass().getResourceAsStream("/top-stories.json")) {
            ObjectReader reader = objectMapper.reader();

            topStories = reader.forType(TopStories.class).<TopStories>readValue(is);
        } catch (IOException e) {
            log.error("Failed to parse cached top-stories.", e);

            topStories = new TopStories();
            topStories.setStories(emptyList());
        }

        return topStories;
    }
}
