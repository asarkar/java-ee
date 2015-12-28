package name.abhijitsarkar.javaee.dailyupdate.repository;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.directory.SearchResult;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Abhijit Sarkar
 */
@FeignClient(serviceId = "${movie-service.id}")
public interface MovieServiceClient {
    @RequestMapping(value = "${movie-service.popular.url}",
            method = GET, produces = APPLICATION_JSON_VALUE)
    public SearchResult findPopularMovies();
}
