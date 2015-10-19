package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import name.abhijitsarkar.javaee.microservices.salon.user.domain.User;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	private PhoneNumberConverter converter = new PhoneNumberConverter();

	@Override
	public List<User> findByPhoneNumEndingWith(String phoneNum) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.phoneNum LIKE %?1%", User.class);

		return query.setParameter(1, converter.convertToDatabaseColumn(phoneNum)).getResultList();
	}
}
