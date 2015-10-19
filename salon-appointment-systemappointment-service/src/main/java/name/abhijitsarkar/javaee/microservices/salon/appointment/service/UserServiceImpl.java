package name.abhijitsarkar.javaee.microservices.salon.appointment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("!test")
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${user-service.url}")
	private String userServiceUrl;

	@Override
	public boolean isValidUser(long userId) {
		try {
			restTemplate.exchange(userServiceUrl + "/users/{userId}", HttpMethod.GET, null, String.class, userId);

			return true;
		} catch (RestClientException e) {
			LOGGER.info("User look up by user id: {} failed.", userId, e);

			return false;
		}
	}
}
