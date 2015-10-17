package name.abhijitsarkar.javaee.microservices.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import name.abhijitsarkar.javaee.microservices.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
