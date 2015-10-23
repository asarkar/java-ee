package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import name.abhijitsarkar.javaee.microservices.salon.user.domain.Authority;

public class AuthorityRepositoryImpl implements AuthorityRepositoryCustom {
	@Autowired
	private EntityManager em;

	@Override
	public UserDetails findUserDetailsByUsername(String username) {
		TypedQuery<Authority> query = em.createQuery("SELECT a FROM Authority a WHERE a.user.email = ?1",
				Authority.class);
		query.setParameter(1, Optional.of(username));

		Collection<? extends GrantedAuthority> authorities = unmodifiableList(query.getResultList().stream()
				.map(a -> a.getRole().name()).map(SimpleGrantedAuthority::new).collect(toList()));

		return new org.springframework.security.core.userdetails.User(username, "***", authorities);
	}
}
