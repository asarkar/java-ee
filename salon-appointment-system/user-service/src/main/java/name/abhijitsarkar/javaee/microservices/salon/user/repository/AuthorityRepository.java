package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import name.abhijitsarkar.javaee.microservices.salon.user.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>, AuthorityRepositoryCustom {
}
