package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import name.abhijitsarkar.javaee.microservices.salon.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
