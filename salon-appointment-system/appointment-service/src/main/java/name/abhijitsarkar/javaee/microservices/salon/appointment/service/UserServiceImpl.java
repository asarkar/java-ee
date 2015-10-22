package name.abhijitsarkar.javaee.microservices.salon.appointment.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.rest.webmvc.RestMediaTypes.HAL_JSON;
import static org.springframework.http.HttpMethod.GET;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.User;
import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.UserSearchResult;

@Service
@Profile("!test")
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${user-service.url}")
	private String userServiceUrl;

	private HttpEntity<Void> dummyEntity;

	@PostConstruct
	void postConstruct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(asList(HAL_JSON));

		dummyEntity = new HttpEntity<Void>(null, headers);
	}

	@Override
	public boolean isValidUser(long userId) {
		try {
			restTemplate.headForHeaders(userServiceUrl + "/users/{userId}", userId);

			return true;
		} catch (RestClientException e) {
			LOGGER.warn("User look up by user id: {} failed.", userId, e);

			return false;
		}
	}

	@Override
	public Collection<Long> getUserIdsByFirstName(String firstName) {
		String findByFirstNameUri = UriComponentsBuilder.fromUriString(userServiceUrl)
				.path("/users/search/findByFirstName").queryParam("firstName", firstName).toUriString();

		ResponseEntity<UserSearchResult> userSearchResult = restTemplate.exchange(findByFirstNameUri, GET, dummyEntity,
				UserSearchResult.class);

		return convertToIds(userSearchResult);
	}

	@Override
	public Collection<Long> getUserIdsByLastName(String lastName) {
		String findByLastNameUri = UriComponentsBuilder.fromUriString(userServiceUrl)
				.path("/users/search/findByLastName").queryParam("lastName", lastName).toUriString();

		ResponseEntity<UserSearchResult> userSearchResult = restTemplate.exchange(findByLastNameUri, GET, dummyEntity,
				UserSearchResult.class);

		return convertToIds(userSearchResult);
	}

	@Override
	public Collection<Long> getUserIdsByFirstAndLastNames(String firstName, String lastName) {
		String findByFirstAndLastNamesUri = UriComponentsBuilder.fromUriString(userServiceUrl)
				.path("/users/search/findByFirstNameAndLastName").queryParam("firstName", firstName)
				.queryParam("lastName", lastName).toUriString();

		ResponseEntity<UserSearchResult> userSearchResult = restTemplate.exchange(findByFirstAndLastNamesUri, GET,
				dummyEntity, UserSearchResult.class);

		return convertToIds(userSearchResult);
	}

	@Override
	public Collection<Long> getUserIdsByEmail(String email) {
		String findByEmailUri = UriComponentsBuilder.fromUriString(userServiceUrl).path("/users/search/findByEmail")
				.queryParam("email", email).toUriString();

		ResponseEntity<UserSearchResult> userSearchResult = restTemplate.exchange(findByEmailUri, GET, dummyEntity,
				UserSearchResult.class);

		return convertToIds(userSearchResult);
	}

	@Override
	public Collection<Long> getUserIdsByPhoneNum(String phoneNum) {
		String findByPhoneNumUri = UriComponentsBuilder.fromUriString(userServiceUrl)
				.path("/users/search/findByPhoneNum").queryParam("phoneNum", phoneNum).toUriString();

		ResponseEntity<UserSearchResult> userSearchResult = restTemplate.exchange(findByPhoneNumUri, GET, dummyEntity,
				UserSearchResult.class);

		return convertToIds(userSearchResult);
	}

	@Override
	public Collection<Long> getUserIdsByPhoneNumEndingWith(String phoneNum) {
		String findByPhoneNumEndingWithUri = UriComponentsBuilder.fromUriString(userServiceUrl)
				.path("/users/search/findByPhoneNumEndingWith").queryParam("phoneNum", phoneNum).toUriString();

		ResponseEntity<UserSearchResult> userSearchResult = restTemplate.exchange(findByPhoneNumEndingWithUri, GET,
				dummyEntity, UserSearchResult.class);

		return convertToIds(userSearchResult);
	}

	private Collection<Long> convertToIds(ResponseEntity<UserSearchResult> userSearchResult) {
		if (userSearchResult.getStatusCode().is2xxSuccessful()) {
			return userSearchResult.getBody().getEmbedded().getUsers().stream().map(User::getUserId).collect(toList());
		}

		return emptyList();
	}
}
