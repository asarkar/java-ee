package name.abhijitsarkar.webservices.jaxrs.hateoas;

import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("slot")
public class AvailabilityResource {
    private AvailabilityService service;

    @GET
    public Response getSlots(@QueryParam("date") String date) {
	Optional<List<Slot>> slots = service.findSlotsByDate(date);

	if (slots.isPresent()) {
	    return ok().entity(slots.get()).build();
	}

	return noContent().status(NOT_FOUND).build();
    }
}
