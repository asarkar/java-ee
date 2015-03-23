package name.abhijitsarkar.microservices.appointment.provider;

import static java.io.File.separator;
import static java.lang.String.join;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import java.io.IOException;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@PatientIdValidated
@Provider
@Priority(Priorities.USER)
public class PatientIdValidatingFilter implements ContainerRequestFilter {
    private static final String SERVICE_NAME = "user-service";
    private static final String SERVICE_URL = join(separator,
	    "http://localhost:8080", SERVICE_NAME, "user");

    @Override
    public void filter(ContainerRequestContext requestContext)
	    throws IOException {
	UriInfo uriInfo = requestContext.getUriInfo();
	List<PathSegment> pathSegments = uriInfo.getPathSegments(true);

	if (pathSegments == null || pathSegments.isEmpty()) {
	    requestContext.abortWith(status(BAD_REQUEST).entity(
		    "'patientId' must be present.").build());
	}

	String patientId = pathSegments.stream()
		.filter(s -> s.getMatrixParameters().containsKey("patientId"))
		.findAny()
		.map(s -> s.getMatrixParameters().getFirst("patientId")).get();

	Response resp = ClientBuilder.newClient().target(SERVICE_URL)
		.path(patientId).matrixParam("type", patientId).request()
		.accept(APPLICATION_JSON).get();

	if (OK.getStatusCode() != resp.getStatus()) {
	    requestContext.abortWith(status(NOT_FOUND).entity(
		    "Patient id: " + patientId + " not found.").build());
	}
    }
}
