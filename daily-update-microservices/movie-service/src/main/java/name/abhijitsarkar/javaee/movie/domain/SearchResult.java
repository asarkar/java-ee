package name.abhijitsarkar.javaee.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import name.abhijitsarkar.javaee.common.domain.Movie;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {
    @JsonProperty("results")
    private Collection<Movie> movies;
}
