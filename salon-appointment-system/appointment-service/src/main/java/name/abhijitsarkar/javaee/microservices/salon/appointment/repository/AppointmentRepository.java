package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import java.time.OffsetDateTime;
import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	// @Param is not necessary if code is compiled with -parameters
	Page<Appointment> findByUserIdIn(@Param("userIds") Collection<Long> userIds, Pageable p);

	Page<Appointment> findByStartTimeGreaterThanEqual(@Param("startTime") OffsetDateTime startTime, Pageable p);

	Page<Appointment> findByStartTimeLessThanEqual(@Param("startTime") OffsetDateTime startTime, Pageable p);

	Page<Appointment> findByStartTimeBetween(@Param("begin") OffsetDateTime begin, @Param("end") OffsetDateTime end,
			Pageable p);

	Page<Appointment> findByUserIdInAndStartTimeBetween(@Param("userIds") Collection<Long> userIds,
			@Param("begin") OffsetDateTime begin, @Param("end") OffsetDateTime end, Pageable p);

	Page<Appointment> findByStartTimeGreaterThanEqualAndEndTimeLessThan(@Param("startTime") OffsetDateTime startTime,
			@Param("endTime") OffsetDateTime endTime, Pageable p);
}
