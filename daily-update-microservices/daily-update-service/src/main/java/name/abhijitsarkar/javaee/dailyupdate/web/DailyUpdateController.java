package name.abhijitsarkar.javaee.dailyupdate.web;

import name.abhijitsarkar.javaee.dailyupdate.repository.MovieServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Abhijit Sarkar
 */
@RestController
@RequestMapping(value = "movies", method = GET, produces = APPLICATION_JSON_VALUE)
public class DailyUpdateController {
    @Autowired
    private MovieServiceClient movieServiceClient;

//    @RequestMapping("popular")
//    public Collection<Movie> findPopularMovies() {
//        return movieServiceClient.findPopularMovies();
//    }
}
