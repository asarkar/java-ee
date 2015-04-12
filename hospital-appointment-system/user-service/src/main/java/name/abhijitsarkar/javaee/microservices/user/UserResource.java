package name.abhijitsarkar.javaee.microservices.user;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.microservices.user.domain.Doctor;
import name.abhijitsarkar.javaee.microservices.user.domain.Patient;
import name.abhijitsarkar.javaee.microservices.user.domain.User;
import name.abhijitsarkar.javaee.microservices.user.service.UserService;

@Path("user")
public class UserResource {
    @Inject
    private UserService userService;

    private List<Doctor> doctors;
    private List<Patient> patients;

    @PostConstruct
    public void initUsers() {
	Objects.requireNonNull(userService);

	doctors = userService.getDoctors();
	patients = userService.getPatients();
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getUsers(@MatrixParam("type") String type) {
	List<? extends User> u = getUsersByType(type);

	if (u != null) {
	    return ok().entity(u).build();
	}

	return noContent().status(NOT_FOUND).build();
    }

    @Path("{id}")
    @GET
    @Produces(APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id,
	    @MatrixParam("type") String type) {
	List<? extends User> u = getUsersByType(type);

	if (u != null) {
	    Optional<? extends User> o = u.stream()
		    .filter(user -> user.getUserId().equals(id)).findAny();

	    if (o.isPresent()) {
		return ok().entity(o.get()).build();
	    }
	}

	return noContent().status(NOT_FOUND).build();
    }

    private List<? extends User> getUsersByType(String type) {
	User.Type userType = null;

	try {
	    userType = User.Type.valueOf(type);
	} catch (IllegalArgumentException e) {
	    return null;
	}

	switch (userType) {
	case Doctor:
	    return doctors;
	case Patient:
	    return patients;
	}

	/*
	 * This can never happen because if the string couldn't be converted to
	 * the enum, we'd already have returned null. Only if the compiler knew
	 * that.
	 */
	return null;
    }

    public void setUsers(UserService userService) {
	this.userService = userService;
    }
}
