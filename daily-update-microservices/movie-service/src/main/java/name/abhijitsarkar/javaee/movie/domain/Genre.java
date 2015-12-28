package name.abhijitsarkar.javaee.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Abhijit Sarkar
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {
    private int id;
    private String name;
}
