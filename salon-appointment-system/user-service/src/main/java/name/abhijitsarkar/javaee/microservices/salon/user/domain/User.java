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
import name.abhijitsarkar.javaee.microservices.salon.common.OptionalConverter;

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
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false, unique = false)
	private String lastName;

	@Column(name = "PHONE_NUM", nullable = false, unique = false)
	private String phoneNum;

	@Column(name = "EMAIL", nullable = true, unique = false)
	@Convert(converter = OptionalConverter.class)
	private Optional<String> email;
}
