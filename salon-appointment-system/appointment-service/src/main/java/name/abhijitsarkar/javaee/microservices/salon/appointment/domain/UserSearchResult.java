package name.abhijitsarkar.javaee.microservices.salon.appointment.domain;

import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import name.abhijitsarkar.javaee.microservices.salon.common.domain.OptionalStringConverter;
import name.abhijitsarkar.javaee.microservices.salon.user.domain.NameConverter;
import name.abhijitsarkar.javaee.microservices.salon.user.domain.PhoneNumberConverter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSearchResult {
	Map<String, Link> links;

}

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class Link {
	private String href;
	private boolean templated;
}

class Embedded {

}

class User {
	private String firstName;

	private String lastName;

	private String phoneNum;

	private Optional<String> email;
	
	Map<String, Link> links;
}
