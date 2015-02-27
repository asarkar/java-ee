package name.abhijitsarkar.webservices.jaxrs.hateoas;

import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("doctor")
public class DoctorResource {
    private List<Doctor> doctors;

    @Path("{id}")
    public Response getDoctor(@PathParam("id") int id) {
	Optional<Doctor> doctor = doctors.stream()
		.filter(doc -> doc.getId() == id).findFirst();

	if (doctor.isPresent()) {
	    return ok().entity(doctor.get()).build();
	}

	return noContent().status(NOT_FOUND).build();
    }
}
