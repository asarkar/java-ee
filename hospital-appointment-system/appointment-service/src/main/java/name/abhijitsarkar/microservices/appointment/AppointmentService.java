package name.abhijitsarkar.microservices.appointment;

import java.util.List;
import java.util.Optional;

public class AppointmentService {
    private List<Appointment> appointments;

    public Optional<Appointment> newAppointment(int slotId) {
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
