package name.abhijitsarkar.javaee.travel.domain;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.couchbase.core.mapping.Document;

/**
 * @author Abhijit Sarkar
 */
@Data
@EqualsAndHashCode(exclude = {"type", "name", "iata", "icao", "callsign", "country"})
public class Airline {
    @Id
    private String id;
    @Field
    private String type;
    @Field
    private String name;
    /* An IATA airport code is a three-letter code designating many airports around the world,
    * defined by the International Air Transport Association (IATA).
    */
    @Field
    private String iata;

    @Field
    private String icao;
    @Field
    private String callsign;
    @Field
    private String country;
}
