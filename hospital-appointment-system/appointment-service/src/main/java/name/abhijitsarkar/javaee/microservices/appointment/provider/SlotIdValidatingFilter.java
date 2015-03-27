package name.abhijitsarkar.javaee.microservices.appointment.provider;

import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@PatientIdValidated
@Provider
@Priority(Priorities.USER)
public class SlotIdValidatingFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext)
	    throws IOException {
	UriInfo uriInfo = requestContext.getUriInfo();
	MultivaluedMap<String, String> pathParams = uriInfo
		.getPathParameters(true);

	if (pathParams == null || pathParams.isEmpty()) {
	    requestContext.abortWith(status(BAD_REQUEST).entity(
		    "{id} must be present.").build());
	}

	String slotId = pathParams.getFirst("id");

	if (slotId == null) {
	    requestContext.abortWith(status(BAD_REQUEST).entity(
		    "{id} must be present.").build());
	}
    }
}
