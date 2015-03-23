package name.abhijitsarkar.microservices.appointment.service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.enterprise.context.ApplicationScoped;

import name.abhijitsarkar.microservices.appointment.domain.Appointment;

@ApplicationScoped
public class AppointmentService {
    private ConcurrentMap<Integer, Appointment> appointmentMap = new ConcurrentHashMap<>();

    public Optional<Appointment> newAppointment(int slotId, String patientId) {
	Appointment appt = appointmentMap.getOrDefault(slotId,
		Appointment.of(slotId, slotId, patientId));

	return appt.getPatientId().equals(patientId) ? Optional
		.of(appointmentMap.computeIfAbsent(slotId, key -> appt))
		: Optional.empty();
    }

    public Optional<Appointment> cancelAppointment(int id, String patientId) {
	return findAppointmentById(id, patientId).isPresent() ? Optional
		.of(appointmentMap.remove(id)) : Optional.empty();
    }

    public Optional<Appointment> findAppointmentById(int id, String patientId) {
	Appointment appt = appointmentMap.get(id);

	return appt != null && appt.getPatientId().equals(patientId) ? Optional
		.of(appt) : Optional.empty();
    }
}
