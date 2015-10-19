package name.abhijitsarkar.javaee.microservices.salon.appointment.repository;

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
		if (!userService.isValidUser(appointment.getUserId())) {
			throw new ResourceNotFoundException(String.format("No user found with id: %s.", appointment.getUserId()));
		}
	}
}
