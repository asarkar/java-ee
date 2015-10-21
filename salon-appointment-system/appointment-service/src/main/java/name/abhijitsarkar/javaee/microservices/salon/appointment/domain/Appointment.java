package name.abhijitsarkar.javaee.microservices.salon.appointment.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
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
import name.abhijitsarkar.javaee.microservices.salon.common.domain.OffsetDateTimeConverter;
import name.abhijitsarkar.javaee.microservices.salon.common.domain.OptionalStringConverter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Wither
@Entity
@Table(name = "APPTS")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 3267192581273265412L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "USER_ID", nullable = false, unique = false)
	private Long userId;

	@Column(name = "START_TIME", nullable = false, unique = false)
	@Convert(converter = OffsetDateTimeConverter.class)
	private OffsetDateTime startTime;

	@Column(name = "END_TIME", nullable = false, unique = false)
	@Convert(converter = OffsetDateTimeConverter.class)
	private OffsetDateTime endTime;

	@Column(name = "COMMENT", nullable = true, unique = false)
	@Convert(converter = OptionalStringConverter.class)
	private Optional<String> comment;
}
