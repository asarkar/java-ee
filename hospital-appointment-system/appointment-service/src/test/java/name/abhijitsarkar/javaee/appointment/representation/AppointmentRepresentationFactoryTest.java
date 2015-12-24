package name.abhijitsarkar.javaee.appointment.representation;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.COALESCE_ARRAYS;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.PRETTY_PRINT;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.UriBuilder;

import name.abhijitsarkar.javaee.appointment.domain.Appointment;

import org.junit.Test;

import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.Representation;

public class AppointmentRepresentationFactoryTest {
    private static final String BASE_URI = "http://localhost:8080/appointment-service/";

    private AppointmentRepresentationFactory factory;
    private UriBuilder apptUriBuilder;

    public AppointmentRepresentationFactoryTest() {
	factory = new AppointmentRepresentationFactory();
	factory.setBaseUri(BASE_URI);

	apptUriBuilder = factory.newAppointmentUriBuilder();
    }

    @Test
    public void testApptRepresentationWithSelfLink() {
	Appointment appt = newApptStub(1);

	Representation rep = factory.newAppointmentRepresentation(appt);

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));

	Link link = rep.getLinkByRel("self");
	assertEquals(apptUriBuilder.build(appt.getId()).toString(),
		link.getHref());
    }

    private Appointment newApptStub(int id) {
	return Appointment.of(id, id, "patient1");
    }

    @Test
    public void testSlotRepresentationWithEditLinks() {
	Appointment appt = newApptStub(1);

	Representation rep = factory.newAppointmentRepresentation(appt);

	System.out.println(rep
		.toString(HAL_JSON, PRETTY_PRINT, COALESCE_ARRAYS));

	Link link = rep.getLinkByRel("edit");
	assertEquals(apptUriBuilder.build(appt.getId()).toString(),
		link.getHref());
    }
}
