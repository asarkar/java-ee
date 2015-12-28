package name.abhijitsarkar.javaee.movie.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import name.abhijitsarkar.javaee.movie.domain.Genre;
import name.abhijitsarkar.javaee.movie.domain.Movie;
import name.abhijitsarkar.javaee.movie.repository.TheMovieDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author Abhijit Sarkar
 */
@Service
public class MovieService {
    @Autowired
    private TheMovieDbClient movieDbClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<Integer, String> genreIdToNameMap;

    @PostConstruct
    void initGenres() throws IOException {
        ObjectReader reader = objectMapper.reader().with(UNWRAP_ROOT_VALUE).withRootName("genres");

        try (InputStream is = getClass().getResourceAsStream("/cached/genres.json")) {
            genreIdToNameMap = reader.forType(new TypeReference<List<Genre>>() {
            }).<List<Genre>>readValue(is).stream().collect(toMap(Genre::getId, Genre::getName));
        }
    }

    public Collection<Movie> findPopularMovies() {
        Collection<Movie> movies = movieDbClient.findPopularMovies().getMovies();

        movies.stream().forEach(m -> {
            List<String> genres = m.getGenreIds().stream().map(genreIdToNameMap::get).collect(toList());

            m.setGenres(genres);
        });

        return movies;
    }
}
