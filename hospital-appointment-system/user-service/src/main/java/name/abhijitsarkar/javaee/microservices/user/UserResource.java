package name.abhijitsarkar.javaee.microservices.user;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.microservices.user.domain.User;
import name.abhijitsarkar.javaee.microservices.user.service.UserService;

@Path("user")
@RequestScoped
public class UserResource {
    @Inject
    private UserService userService;

    @GET
    @Produces(APPLICATION_JSON)
    public Response getUsers(@MatrixParam("type") String type) {
	List<? extends User> u = userService.getUsersByType(type);

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
	List<? extends User> u = userService.getUsersByType(type);

	if (u != null) {
	    Optional<? extends User> o = u.stream()
		    .filter(user -> user.getUserId().equals(id)).findAny();

	    if (o.isPresent()) {
		return ok().entity(o.get()).build();
	    }
	}

	return noContent().status(NOT_FOUND).build();
    }

    public void setUserService(UserService userService) {
	this.userService = userService;
    }
}
