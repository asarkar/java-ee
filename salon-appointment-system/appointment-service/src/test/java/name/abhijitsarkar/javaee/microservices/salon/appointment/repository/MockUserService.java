package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import org.springframework.stereotype.Service;

import name.abhijitsarkar.javaee.microservices.salon.appointment.service.UserService;

@Service
public class MockUserService implements UserService {
	@Override
	public boolean isValidUser(long userId) {
		return true;
	}
}
