package name.abhijitsarkar.javaee.microservices.salon.common;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class UserDetailsDeserializer extends JsonDeserializer<User> {
	Collection<? extends GrantedAuthority> NO_AUTHORITIES = emptyList();

	@Override
	public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);

		JsonNode username = node.path("username");
		JsonNode password = node.path("password");
		JsonNode authorities = node.path("authorities");

		if (username.isMissingNode() || password.isMissingNode()) {
			throw new IOException("Username or password not found.");
		}

		if (!authorities.has(0)) {
			return new User(username.asText(), password.asText(), NO_AUTHORITIES);
		}

		List<GrantedAuthority> roles = authorities.findValuesAsText("authority").stream()
				.map(SimpleGrantedAuthority::new).collect(toList());

		return new User(username.asText(), password.asText(), unmodifiableList(roles));
	}
}
