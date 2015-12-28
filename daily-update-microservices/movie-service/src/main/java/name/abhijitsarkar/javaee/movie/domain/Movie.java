package name.abhijitsarkar.javaee.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private long id;
    private String title;
    private String releaseDate;
    private float popularity;
    @JsonProperty("overview")
    private String plot;
    private Collection<String> genreIds;
    private Collection<String> genres;
}
