package name.abhijitsarkar.webservices.jaxrs.hateoas;

import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("appointment")
public class AppointmentResource {
    private AppointmentService service;

    @Path("{id}")
    @GET
    public Response getAppointment(@PathParam("id") int id) {
	Optional<Appointment> appointment = service.findAppointmentById(id);

	if (appointment.isPresent()) {
	    return ok().entity(appointment.get()).build();
	}

	return noContent().status(NOT_FOUND).build();
    }

    @Path("{id}")
    @POST
    public Response makeAppointment(@PathParam("id") int id) {
	service.newAppointment(id);

	return Response.created(null).build();
    }

    @Path("{id}")
    @DELETE
    public Response cancelAppointment(@PathParam("id") int id) {
	Optional<Appointment> appointment = service.cancelAppointment(id);

	if (appointment.isPresent()) {
	    return ok().status(NOT_FOUND).build();
	}

	return noContent().status(NOT_FOUND).build();
    }

}
