package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

import static com.google.common.base.Preconditions.checkArgument;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import name.abhijitsarkar.javaee.microservices.salon.appointment.domain.Appointment;
import name.abhijitsarkar.javaee.microservices.salon.appointment.service.UserService;

@RepositoryEventHandler(Appointment.class)
@Component
public class AppointmentEventHandler {
	@Autowired
	private UserService userService;

	@HandleBeforeCreate
	public void beforeCreatingAppointment(Appointment appointment) {
		OffsetDateTime now = OffsetDateTime.now();

		checkArgument(!appointment.getStartDateTime().isBefore(now), "Cannot create an appointment in the past.");
		checkArgument(appointment.getEndDateTime().isAfter(appointment.getStartDateTime()),
				"Appointment end datetime must be after start datetime.");

		if (!userService.isValidUser(appointment.getUserId())) {
			throw new ResourceNotFoundException(String.format("No user found with id: %s.", appointment.getUserId()));
		}
	}
}
