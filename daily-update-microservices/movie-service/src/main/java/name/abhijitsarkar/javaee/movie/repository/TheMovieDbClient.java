package name.abhijitsarkar.javaee.movie.repository;

import name.abhijitsarkar.javaee.movie.domain.SearchResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Abhijit Sarkar
 */
@FeignClient(url = "${themoviedb.url}?api_key=${themoviedb.apiKey}")
@Profile("live")
public interface TheMovieDbClient {
    @RequestMapping(value = "${themoviedb.popular.url}",
            method = GET, produces = APPLICATION_JSON_VALUE)
    public SearchResult findPopularMovies();
}
