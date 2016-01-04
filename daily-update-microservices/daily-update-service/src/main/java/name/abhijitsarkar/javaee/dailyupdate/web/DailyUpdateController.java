package name.abhijitsarkar.javaee.dailyupdate.web;

import name.abhijitsarkar.javaee.common.domain.Movie;
import name.abhijitsarkar.javaee.common.domain.Story;
import name.abhijitsarkar.javaee.dailyupdate.repository.MovieServiceClient;
import name.abhijitsarkar.javaee.dailyupdate.repository.NewsServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Abhijit Sarkar
 */
@RestController
@RequestMapping(path = "dailyupdate", method = GET, produces = APPLICATION_JSON_VALUE)
public class DailyUpdateController {
    @Autowired
    private MovieServiceClient movieServiceClient;
    @Autowired
    private NewsServiceClient newsServiceClient;

    @RequestMapping("movies/popular")
    public Collection<Movie> findPopularMovies() {
        return movieServiceClient.findPopularMovies();
    }

    @RequestMapping(value = "news/topstories")
    public Collection<Story> getTopStories(
            @RequestParam(value = "sections", required = false) Collection<String> sections) {
        return newsServiceClient.getTopStories(sections);
    }
}
