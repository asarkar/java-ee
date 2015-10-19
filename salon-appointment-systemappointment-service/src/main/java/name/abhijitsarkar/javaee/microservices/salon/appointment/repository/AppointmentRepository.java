package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	// @Param is not necessary if code is compiled with -parameters
	List<Appointment> findByUserId(@Param("userId") Long userId);

	List<Appointment> findByStartTimeGreaterThanEqual(@Param("startTime") OffsetDateTime startTime);
	
	List<Appointment> findByStartTimeLessThanEqual(@Param("startTime") OffsetDateTime startTime);
	
	List<Appointment> findByStartTimeBetween(@Param("begin") OffsetDateTime begin, @Param("end") OffsetDateTime end);
}
