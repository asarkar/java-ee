package name.abhijitsarkar.microservices.availability;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static name.abhijitsarkar.microservices.availability.representation.AvailabilityRepresentationFactory.BASE_PATH;
import static name.abhijitsarkar.microservices.availability.representation.AvailabilityRepresentationFactory.SLOT_PATH;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import name.abhijitsarkar.microservices.availability.domain.Slot;
import name.abhijitsarkar.microservices.availability.service.AvailabilityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(BASE_PATH)
public class AvailabilityResource {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AvailabilityResource.class);

    @Inject
    private AvailabilityService service;

    @GET
    @Produces(HAL_JSON)
    // @DateValidated
    public Response getSlots(@QueryParam("date") String date) {
	Optional<List<Slot>> slots = service.findSlotsByDate(date);

	if (slots.isPresent()) {
	    List<Slot> l = slots.get();

	    LOGGER.info("Found {} slots for the day: {}.", l.size(), date);

	    Link link = Link.fromUri("slot/{id}").rel("start")
		    .type(APPLICATION_JSON).build(l.get(0).getId());

	    LOGGER.info("Added link 'start': {}.", link.toString());

	    return ok().entity(l).links(link).build();
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
	    ResponseBuilder builder = ok().entity(slot.get());

	    LOGGER.info("Found slot with id: {}.", id);

	    if (service.findSlotById(id + 1).isPresent()) {
		Link next = Link.fromUri("slot/{id}").rel("next")
			.type(APPLICATION_JSON).build(id + 1);

		builder.links(next);

		LOGGER.info("Added link 'next': {}.", next.toString());
	    }

	    if (service.findSlotById(id - 1).isPresent()) {
		Link prev = Link.fromUri("slot/{id}").rel("prev")
			.type(APPLICATION_JSON).build(id - 1);
		builder.links(prev);

		LOGGER.info("Added link 'prev': {}.", prev.toString());
	    }

	    return builder.build();
	}

	LOGGER.info("Didn't find any slots with id: {}.", id);

	return noContent().status(NOT_FOUND).build();
    }
}
