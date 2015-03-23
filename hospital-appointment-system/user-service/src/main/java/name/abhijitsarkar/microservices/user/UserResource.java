package name.abhijitsarkar.microservices.user;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {
    @Inject
    private Users users;

    private List<Doctor> doctors;
    private List<Patient> patients;

    public void initUsers() {
	Objects.requireNonNull(users);

	doctors = users.getDoctors();
	patients = users.getPatients();
    }

    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id,
	    @MatrixParam("type") String type) {
	List<? extends User> u = getUsers(type);

	if (u != null) {
	    Optional<? extends User> o = u.stream()
		    .filter(user -> user.getUserId().equals(id)).findAny();

	    if (o.isPresent()) {
		return ok().entity(o.get()).build();
	    }
	}

	return noContent().status(NOT_FOUND).build();
    }

    private List<? extends User> getUsers(String type) {
	switch (type) {
	case "Doctor":
	    return doctors;
	case "Patient":
	    return patients;
	default:
	    return null;
	}
    }

    public void setUsers(Users users) {
	this.users = users;
    }
}
