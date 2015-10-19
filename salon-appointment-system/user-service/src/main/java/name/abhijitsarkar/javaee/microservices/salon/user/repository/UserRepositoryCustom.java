package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import name.abhijitsarkar.javaee.microservices.salon.user.domain.User;

public interface UserRepositoryCustom {
	public List<User> findByPhoneNumEndingWith(@Param("phoneNum") String phoneNum);
}
