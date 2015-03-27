package name.abhijitsarkar.javaee.microservices.appointment.representation;

import java.util.Objects;

import javax.enterprise.context.Dependent;
import javax.ws.rs.core.UriBuilder;

import name.abhijitsarkar.javaee.microservices.appointment.domain.Appointment;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

@Dependent
public class AppointmentRepresentationFactory extends
	StandardRepresentationFactory {
    public static final String BASE_PATH = "appointment";
    public static final String APPOINTMENT_PATH = "{id}";

    private String baseUri;

    public void setBaseUri(String baseUri) {
	this.baseUri = baseUri;
    }

    UriBuilder newAppointmentUriBuilder() {
	Objects.requireNonNull(baseUri);

	return UriBuilder.fromUri(baseUri).path(BASE_PATH)
		.path(APPOINTMENT_PATH);
    }

    public Representation newAppointmentRepresentation(Appointment appt) {
	UriBuilder apptUriBuilder = newAppointmentUriBuilder();

	Representation rep = newRepresentation(apptUriBuilder.build(
		appt.getId()).toString());

	rep.withLink("edit", apptUriBuilder.build(appt.getId()).toString(),
		"cancel", "cancel", null, null);

	rep.withBean(appt);

	return rep;
    }
}
