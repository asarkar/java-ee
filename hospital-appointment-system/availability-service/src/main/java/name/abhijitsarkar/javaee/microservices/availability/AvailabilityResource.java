package name.abhijitsarkar.javaee.microservices.availability;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.COALESCE_ARRAYS;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static com.theoryinpractise.halbuilder.api.RepresentationFactory.PRETTY_PRINT;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static name.abhijitsarkar.javaee.microservices.availability.representation.AvailabilityRepresentationFactory.BASE_PATH;
import static name.abhijitsarkar.javaee.microservices.availability.representation.AvailabilityRepresentationFactory.SLOT_PATH;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import name.abhijitsarkar.javaee.microservices.availability.domain.Slot;
import name.abhijitsarkar.javaee.microservices.availability.representation.AvailabilityRepresentationFactory;
import name.abhijitsarkar.javaee.microservices.availability.service.AvailabilityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(BASE_PATH)
@RequestScoped
public class AvailabilityResource {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AvailabilityResource.class);
    private static final URI[] HAL_JSON_FLAGS = new URI[] { PRETTY_PRINT,
	    COALESCE_ARRAYS };

    @Inject
    private AvailabilityService service;

    @Inject
    private AvailabilityRepresentationFactory repFactory;

    @Context
    private UriInfo uriInfo;

    @PostConstruct
    public void postConstruct() {
	repFactory.setBaseUri(uriInfo.getBaseUri().toString());
    }

    @GET
    @Produces(HAL_JSON)
    // @DateValidated
    public Response getSlots(@QueryParam("date") String date) {
	Optional<List<Slot>> slots = service.findSlotsByDate(date);

	if (slots.isPresent()) {
	    List<Slot> l = slots.get();

	    LOGGER.info("Found {} slots for the day: {}.", l.size(), date);

	    return ok().entity(
		    repFactory.newSlotsRepresentation(l).toString(HAL_JSON,
			    HAL_JSON_FLAGS)).build();
	}

	LOGGER.info("Didn't find any slots for the day: {}.", date);

	return noContent().status(NOT_FOUND).build();
    }

    @Path(SLOT_PATH)
    @GET
    @Produces(HAL_JSON)
    public Response getSlot(@PathParam("id") int id) {
	Optional<Slot> slot = service.findSlotById(id);

	if (slot.isPresent()) {
	    LOGGER.info("Found slot with id: {}.", id);

	    Optional<Slot> previousSlot = service.findSlotById(id - 1);
	    Optional<Slot> nextSlot = service.findSlotById(id + 1);

	    return ok().entity(
		    repFactory.newSlotRepresentation(slot.get(), previousSlot,
			    nextSlot).toString(HAL_JSON, HAL_JSON_FLAGS))
		    .build();
	}

	LOGGER.info("Didn't find any slots with id: {}.", id);

	return noContent().status(NOT_FOUND).build();
    }

    @Path(SLOT_PATH)
    @PUT
    @Produces(HAL_JSON)
    public Response reserveOrRelinquishSlot(@PathParam("id") int id,
	    @QueryParam("reserve") boolean reserve) {
	Optional<Slot> slot = service.updateSlotAvailability(id, reserve);

	if (slot.isPresent()) {
	    LOGGER.info("{} slot with id: {}.", reserve ? "Reserved"
		    : "Relinquished", id);

	    return getSlot(id);
	}

	if (!service.findSlotById(id).isPresent()) {
	    return noContent().status(NOT_FOUND).build();
	}

	return noContent().status(CONFLICT).build();
    }
}
