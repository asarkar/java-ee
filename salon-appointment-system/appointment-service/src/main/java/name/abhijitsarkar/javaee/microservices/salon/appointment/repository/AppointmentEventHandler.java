package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import static com.google.common.base.Preconditions.checkArgument;

import java.time.OffsetDateTime;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.Appointment;

@RepositoryEventHandler(Appointment.class)
@Component
public class AppointmentEventHandler {
	@HandleBeforeCreate
	public void beforeCreatingAppointment(Appointment appointment) {
		OffsetDateTime now = OffsetDateTime.now();

		checkArgument(!appointment.getStartDateTime().isBefore(now), "Cannot create an appointment in the past.");
		checkArgument(appointment.getEndDateTime().isAfter(appointment.getStartDateTime()),
				"Appointment end datetime must be after start datetime.");
	}
}
