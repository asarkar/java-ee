package name.abhijitsarkar.javaee.microservices.salon.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = UserDetailsDeserializer.class)
public abstract class UserDetailsMixin {

}
