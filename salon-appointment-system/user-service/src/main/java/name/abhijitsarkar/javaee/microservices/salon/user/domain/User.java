package name.abhijitsarkar.javaee.microservices.salon.user.domain;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import name.abhijitsarkar.javaee.microservices.salon.common.OptionalStringConverter;
import name.abhijitsarkar.javaee.microservices.salon.user.repository.NameConverter;
import name.abhijitsarkar.javaee.microservices.salon.user.repository.PhoneNumberConverter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Wither
@Entity
@Table(name = "USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 262950482349139355L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "FIRST_NAME", nullable = false, unique = false)
	@Convert(converter = NameConverter.class)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false, unique = false)
	@Convert(converter = NameConverter.class)
	private String lastName;

	@Column(name = "PHONE_NUM", nullable = false, unique = false)
	@Convert(converter = PhoneNumberConverter.class)
	private String phoneNum;

	@Column(name = "EMAIL", nullable = true, unique = false)
	@Convert(converter = OptionalStringConverter.class)
	private Optional<String> email;
}
