package name.abhijitsarkar.webservices.jaxrs.hateoas;

import java.util.List;
import java.util.Optional;

public class AppointmentService {
    private List<Appointment> appointments;
    private AvailabilityService service;

    public Optional<Appointment> newAppointment(int id) {
	return null;
    }

    public Optional<Appointment> cancelAppointment(int id) {
	return null;

    }

    public Optional<Appointment> findAppointmentById(int id) {
	return appointments.stream().filter(appt -> appt.getId() == id)
		.findFirst();
    }

}
