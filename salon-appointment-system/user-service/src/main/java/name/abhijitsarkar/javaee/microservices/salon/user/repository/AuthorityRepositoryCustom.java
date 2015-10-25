package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthorityRepositoryCustom {
	public UserDetails loadUserByUsername(String username);
}
