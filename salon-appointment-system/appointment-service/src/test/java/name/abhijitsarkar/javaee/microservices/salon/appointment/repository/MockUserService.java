package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.springframework.stereotype.Service;

import name.abhijitsarkar.javaee.microservices.salon.appointment.service.UserService;

@Service
public class MockUserService implements UserService {
	private static final Collection<Long> MOCK_USER_IDS = asList(1L);

	@Override
	public boolean isValidUser(long userId) {
		return true;
	}

	@Override
	public Collection<Long> getUserIdsByFirstName(String firstName) {
		return MOCK_USER_IDS;
	}

	@Override
	public Collection<Long> getUserIdsByLastName(String lastName) {
		return MOCK_USER_IDS;
	}

	@Override
	public Collection<Long> getUserIdsByFirstAndLastNames(String firstName, String lastName) {
		return MOCK_USER_IDS;
	}

	@Override
	public Collection<Long> getUserIdsByEmail(String email) {
		return MOCK_USER_IDS;
	}

	@Override
	public Collection<Long> getUserIdsByPhoneNum(String phoneNum) {
		return MOCK_USER_IDS;
	}

	@Override
	public Collection<Long> getUserIdsByPhoneNumEndingWith(String phoneNum) {
		return MOCK_USER_IDS;
	}
}
