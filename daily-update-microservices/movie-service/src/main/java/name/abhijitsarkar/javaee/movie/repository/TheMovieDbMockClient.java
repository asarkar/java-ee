package name.abhijitsarkar.javaee.movie.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.abhijitsarkar.javaee.movie.domain.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Abhijit Sarkar
 */
@Profile("!live")
@Repository
public class TheMovieDbMockClient implements TheMovieDbClient {
    @Autowired
    private ObjectMapper objectMapper;

    private SearchResult result;

    @PostConstruct
    void initSearchResult() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/cached/popular-movies.json")) {
            result = objectMapper.readValue(is, SearchResult.class);
        }
    }

    @Override
    public SearchResult findPopularMovies() {
        return result;
    }
}
