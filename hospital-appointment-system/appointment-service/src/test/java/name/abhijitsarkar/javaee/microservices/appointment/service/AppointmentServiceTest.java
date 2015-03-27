package name.abhijitsarkar.javaee.microservices.appointment.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import name.abhijitsarkar.javaee.microservices.appointment.domain.Appointment;
import name.abhijitsarkar.javaee.microservices.appointment.service.AppointmentService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppointmentServiceTest {
    private AppointmentService service = new AppointmentService();
    private int slotId = 1;
    private String patientId = "patient1";

    @Before
    public void makeNewAppointment() {
	Optional<Appointment> appt = service.newAppointment(slotId, patientId);
	assertTrue(appt.isPresent());
    }

    @After
    public void CancelAppointment() {
	service.cancelAppointment(slotId, patientId);
    }

    @Test
    public void testFindAppointment() {
	Optional<Appointment> appt = service.findAppointmentById(slotId,
		patientId);
	assertTrue(appt.isPresent());
    }

    @Test
    public void testCancelAppointment() {
	Optional<Appointment> appt = service.cancelAppointment(slotId,
		patientId);
	assertTrue(appt.isPresent());

	appt = service.findAppointmentById(slotId, patientId);
	assertFalse(appt.isPresent());
    }

    @Test
    public void testCancelAppointmentWhenUnauthorized() {
	Optional<Appointment> appt = service.cancelAppointment(slotId,
		"someOtherPatient");
	assertFalse(appt.isPresent());

	appt = service.findAppointmentById(slotId, patientId);
	assertTrue(appt.isPresent());
    }

    @Test
    public void testFindAppointmentWhenUnauthorized() {
	Optional<Appointment> appt = service.findAppointmentById(slotId,
		"someOtherPatient");
	assertFalse(appt.isPresent());
    }
}
