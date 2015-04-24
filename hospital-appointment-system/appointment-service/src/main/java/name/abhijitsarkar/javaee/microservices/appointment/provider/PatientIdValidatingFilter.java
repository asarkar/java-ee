package name.abhijitsarkar.javaee.microservices.appointment.provider;

import static java.io.File.separator;
import static java.lang.String.join;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static name.abhijitsarkar.javaee.microservices.user.domain.User.Type.Patient;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.Client;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
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

    @Inject
    private Client client;

    @Override
    public void filter(ContainerRequestContext requestContext)
	    throws IOException {
	UriInfo uriInfo = requestContext.getUriInfo();
	MultivaluedMap<String, String> queryParams = uriInfo
		.getQueryParameters(true);

	if (queryParams == null || queryParams.isEmpty()) {
	    requestContext.abortWith(status(BAD_REQUEST).entity(
		    "'patientId' must be present.").build());
	}

	String patientId = queryParams.getFirst("patientId");

	if (patientId == null) {
	    requestContext.abortWith(status(BAD_REQUEST).entity(
		    "'patientId' must be present.").build());
	}

	Response resp = client.target(SERVICE_URL).path(patientId)
		.matrixParam("type", Patient.name()).request()
		.accept(APPLICATION_JSON).get();

	if (OK.getStatusCode() != resp.getStatus()) {
	    requestContext.abortWith(status(NOT_FOUND).entity(
		    "Patient id: " + patientId + " not found.").build());
	}
    }
}
