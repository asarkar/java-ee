package name.abhijitsarkar.javaee.microservices.user.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Wither
@Entity
@Table(name = "USERS")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@Column(name = "FIRST_NAME", nullable = false, unique = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false, unique = false)
	private String lastName;

	@Column(name = "PHONE_NUM", nullable = false, unique = false)
	private String phoneNum;

	@Column(name = "EMAIL", nullable = true, unique = false)
	private String email;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Optional<String> getEmail() {
		return Optional.ofNullable(email);
	}

	public void setEmail(Optional<String> email) {
		this.email = (email == null ? null : email.orElse(null));
	}
}
