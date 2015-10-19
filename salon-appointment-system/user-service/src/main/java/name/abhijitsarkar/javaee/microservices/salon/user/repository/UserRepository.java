package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import name.abhijitsarkar.javaee.microservices.salon.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByFirstName(@Param("firstName") String firstName);

	public List<User> findByLastName(@Param("lastName") String lastName);

	public List<User> findByFirstNameAndLastName(@Param("firstName") String firstName,
			@Param("lastName") String lastName);

	public List<User> findByEmail(@Param("email") Optional<String> email);

	public List<User> findByPhoneNum(@Param("phoneNum") String phoneNum);

	public List<User> findByPhoneNumEndingWith(@Param("phoneNum") String phoneNum);
}
