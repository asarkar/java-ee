package name.abhijitsarkar.microservices.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Doctor extends User {
    @JsonCreator
    public Doctor(@JsonProperty("userId") String userId,
	    @JsonProperty("firstName") String firstName,
	    @JsonProperty("lastName") String lastName) {
	super(userId, firstName, lastName);
    }
}
