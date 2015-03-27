package name.abhijitsarkar.javaee.microservices.appointment;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.COALESCE_ARRAYS;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.PRETTY_PRINT;
import static javax.ws.rs.HttpMethod.PUT;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static name.abhijitsarkar.javaee.microservices.appointment.representation.AppointmentRepresentationFactory.APPOINTMENT_PATH;
import static name.abhijitsarkar.javaee.microservices.appointment.representation.AppointmentRepresentationFactory.BASE_PATH;

import java.io.StringReader;
import java.net.URI;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.microservices.appointment.domain.Appointment;
import name.abhijitsarkar.javaee.microservices.appointment.provider.PatientIdValidated;
import name.abhijitsarkar.javaee.microservices.appointment.provider.SlotIdValidated;
import name.abhijitsarkar.javaee.microservices.appointment.representation.AppointmentRepresentationFactory;
import name.abhijitsarkar.javaee.microservices.appointment.service.AppointmentService;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;

@RequestScoped
@PatientIdValidated
@Path(BASE_PATH)
public class AppointmentResource {
    private static final URI[] HAL_JSON_FLAGS = new URI[] { PRETTY_PRINT,
	    COALESCE_ARRAYS };

    /*
     * A client is a heavy-weight object and to avoid resource leaks, should be
     * properly disposed off when it has served it's purpose. This way, we can
     * manage it's lifecycle better.
     */
    @Inject
    private AvailabilityClientFactory clientFactory;

    @Inject
    private AppointmentService service;

    @Inject
    private AppointmentRepresentationFactory repFactory;

    @Path(APPOINTMENT_PATH)
    @GET
    @Produces(HAL_JSON)
    public Response getAppointment(@PathParam("id") int id,
	    @QueryParam("patientId") String patientId) {
	Optional<Appointment> appointment = service.findAppointmentById(id,
		patientId);

	if (appointment.isPresent()) {
	    return ok().entity(
		    repFactory.newAppointmentRepresentation(appointment.get())
			    .toString(HAL_JSON, HAL_JSON_FLAGS)).build();
	}

	return noContent().status(NOT_FOUND).build();
    }

    @SlotIdValidated
    @Path(APPOINTMENT_PATH)
    @POST
    @Produces(HAL_JSON)
    public Response makeAppointment(@PathParam("id") int id,
	    @QueryParam("patientId") String patientId) {
	Response response = findSlotById(id);

	if (isOk(response)) {
	    String slot = response.readEntity(String.class);
	    ContentRepresentation rep = repFactory.readRepresentation(HAL_JSON,
		    new StringReader(slot));

	    String reservationUri = findEditUriWithTitle(rep, "reserve");
	    response = updateSlotAvailability(reservationUri);

	    if (isOk(response)) {
		Optional<Appointment> appointment = service.cancelAppointment(
			id, patientId);

		if (appointment.isPresent()) {
		    return ok().entity(
			    repFactory.newAppointmentRepresentation(
				    appointment.get()).toString(HAL_JSON,
				    HAL_JSON_FLAGS)).build();
		}

		String cancelationUri = findEditUriWithTitle(rep, "relinquish");
		updateSlotAvailability(cancelationUri);

		return noContent().status(CONFLICT).build();
	    }

	    return noContent().status(response.getStatus()).build();
	}

	return noContent().status(NOT_FOUND).build();
    }

    @Path(APPOINTMENT_PATH)
    @DELETE
    @Produces(HAL_JSON)
    public Response cancelAppointment(@PathParam("id") int id,
	    @QueryParam("patientId") String patientId) {
	Response response = findSlotById(id);

	if (isOk(response)) {
	    String slot = response.readEntity(String.class);
	    ContentRepresentation rep = repFactory.readRepresentation(HAL_JSON,
		    new StringReader(slot));

	    String cancelationUri = findEditUriWithTitle(rep, "relinquish");

	    response = updateSlotAvailability(cancelationUri);

	    if (isOk(response)) {
		Optional<Appointment> appointment = service.cancelAppointment(
			id, patientId);

		if (appointment.isPresent()) {
		    return ok().entity(
			    repFactory.newAppointmentRepresentation(
				    appointment.get()).toString(HAL_JSON,
				    HAL_JSON_FLAGS)).build();
		}

		String reservationUri = findEditUriWithTitle(rep, "reserve");

		updateSlotAvailability(reservationUri);

		return noContent().status(CONFLICT).build();
	    }

	    return noContent().status(response.getStatus()).build();
	}

	return noContent().status(NOT_FOUND).build();
    }

    private boolean isOk(Response response) {
	return response.getStatus() == OK.getStatusCode();
    }

    private String findEditUriWithTitle(ContentRepresentation rep, String title) {
	return rep.getLinksByRel("edit").stream()
		.filter(l -> l.getTitle().equals(title)).findAny()
		.map(l -> l.getHref()).get();
    }

    private Response findSlotById(int id) {
	return clientFactory.newSlotTargetBuilder(id).get();
    }

    private Response updateSlotAvailability(String uri) {
	return clientFactory.newTargetBuilder(uri).method(PUT);
    }
}
