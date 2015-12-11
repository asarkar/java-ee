package name.abhijitsarkar.javaee.travel.domain;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.Data;

/**
 * @author Abhijit Sarkar
 */
@Data
public class Airport {
    @Id
    private String id;
    @Field
    private String type;
    @Field("airportname")
    private String name;
    @Field
    private String city;
    @Field
    private String country;
    @Field
    private String faa;
    /*
    * The ICAO (/ˌaɪˌkeɪˈoʊ/, "I-K-O") airport code or location indicator is a four-character alphanumeric code
    * designating aerodromes around the world. These codes are defined by the International Civil Aviation Organization.
    */
    @Field
    private String icao;
    @Field("tz")
    private String timeZone;
    @Field("geo")
    private Location location;

    @Data
    class Location {
        @Field("lat")
        private double latitude;
        @Field("lon")
        private double longitude;
        @Field("alt")
        private double altitude;
    }
}
