package name.abhijitsarkar.javaee.microservices.appointment;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static javax.ws.rs.HttpMethod.PUT;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static name.abhijitsarkar.javaee.microservices.appointment.representation.AppointmentRepresentationFactory.APPOINTMENT_PATH;
import static name.abhijitsarkar.javaee.microservices.appointment.representation.AppointmentRepresentationFactory.BASE_PATH;

import java.net.URI;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import name.abhijitsarkar.javaee.microservices.appointment.domain.Appointment;
import name.abhijitsarkar.javaee.microservices.appointment.provider.PatientIdValidated;
import name.abhijitsarkar.javaee.microservices.appointment.provider.SlotIdValidated;
import name.abhijitsarkar.javaee.microservices.appointment.representation.AppointmentRepresentationFactory;
import name.abhijitsarkar.javaee.microservices.appointment.service.AppointmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;

@RequestScoped
@PatientIdValidated
@Path(BASE_PATH)
public class AppointmentResource {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AppointmentResource.class);

    /*
     * A client is a heavy-weight object and to avoid resource leaks, should be
     * properly disposed off when it has served it's purpose. This way, we can
     * manage it's lifecycle better.
     */
    @Inject
    private AvailabilityServiceClientFactory clientFactory;

    @Inject
    private AppointmentService service;

    @Inject
    private AppointmentRepresentationFactory repFactory;

    @Context
    private UriInfo uriInfo;

    @PostConstruct
    public void postConstruct() {
	repFactory.setBaseUri(uriInfo.getBaseUri().toString());
    }

    @Path(APPOINTMENT_PATH)
    @GET
    @Produces(HAL_JSON)
    public Response getAppointment(@PathParam("id") int id,
	    @QueryParam("patientId") String patientId) {
	Optional<Appointment> appointment = service.findAppointmentById(id,
		patientId);

	if (appointment.isPresent()) {
	    LOGGER.info("Found appointment with id: {}.", id);

	    Appointment appt = appointment.get();

	    Representation rep = repFactory.newAppointmentRepresentation(appt);

	    return ok().entity(repFactory.convertToString(rep)).build();
	}

	LOGGER.error("No appointment found with id: {}.", id);

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
	    LOGGER.info("Found slot with id: {}.", id);

	    String slot = response.readEntity(String.class);
	    ContentRepresentation content = repFactory.getContent(slot);

	    String reservationUri = repFactory.findRelUriWithTitle("edit",
		    content, "reserve");

	    LOGGER.info("Found slot reservation URI: {}.", reservationUri);
	    LOGGER.info("Attempting to reserve slot...");

	    response = updateSlotAvailability(reservationUri);

	    if (isOk(response)) {
		LOGGER.info("Successfully reserved slot.");

		Optional<Appointment> appointment = service.newAppointment(id,
			patientId);

		if (appointment.isPresent()) {
		    Appointment appt = appointment.get();

		    Representation rep = repFactory
			    .newAppointmentRepresentation(appt);

		    URI repURI = repFactory.getRepresentationURI(rep);

		    LOGGER.info("Successfully created appointment: {}.", repURI);

		    return created(repURI).entity(
			    repFactory.convertToString(rep)).build();
		}

		LOGGER.error("Failed to create appointment. Attempting to relinquish slot...");

		String cancelationUri = repFactory.findRelUriWithTitle("edit",
			content, "relinquish");

		LOGGER.info("Found slot cancelation URI: {}.", cancelationUri);

		updateSlotAvailability(cancelationUri);

		return noContent().status(CONFLICT).build();
	    }

	    LOGGER.error("Failed to reserve slot.");

	    return noContent().status(response.getStatus()).build();
	}

	LOGGER.error("No slot found with id: {}.", id);

	return noContent().status(NOT_FOUND).build();
    }

    @Path(APPOINTMENT_PATH)
    @DELETE
    @Produces(HAL_JSON)
    public Response cancelAppointment(@PathParam("id") int id,
	    @QueryParam("patientId") String patientId) {
	Response response = findSlotById(id);

	if (isOk(response)) {
	    LOGGER.info("Found slot with id: {}.", id);

	    String slot = response.readEntity(String.class);
	    ContentRepresentation content = repFactory.getContent(slot);

	    String cancelationUri = repFactory.findRelUriWithTitle("edit",
		    content, "relinquish");

	    LOGGER.info("Found slot calcelation URI: {}.", cancelationUri);
	    LOGGER.info("Attempting to relinquish slot...");

	    response = updateSlotAvailability(cancelationUri);

	    if (isOk(response)) {
		LOGGER.info("Successfully relinquished slot.");

		Optional<Appointment> appointment = service.cancelAppointment(
			id, patientId);

		if (appointment.isPresent()) {
		    Appointment appt = appointment.get();

		    Representation rep = repFactory
			    .newAppointmentRepresentation(appt);

		    URI repURI = repFactory.getRepresentationURI(rep);

		    LOGGER.info("Successfully canceled appointment: {}.",
			    repURI);

		    return ok().entity(repFactory.convertToString(rep)).build();
		}

		LOGGER.error("Failed to cancel appointment. Attempting to reserve slot...");

		String reservationUri = repFactory.findRelUriWithTitle("edit",
			content, "reserve");

		LOGGER.info("Found slot reservation URI: {}.", reservationUri);

		updateSlotAvailability(reservationUri);

		return noContent().status(CONFLICT).build();
	    }

	    LOGGER.error("Failed to relinquish slot.");

	    return noContent().status(response.getStatus()).build();
	}

	LOGGER.error("No slot found with id: {}.", id);

	return noContent().status(NOT_FOUND).build();
    }

    private boolean isOk(Response response) {
	return response.getStatus() == OK.getStatusCode();
    }

    private Response findSlotById(int id) {
	return clientFactory.newSlotTargetBuilder(id).get();
    }

    private Response updateSlotAvailability(String uri) {
	return clientFactory.newTargetBuilder(uri).method(PUT);
    }
}
