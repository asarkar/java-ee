package name.abhijitsarkar.webservices.jaxrs.subresource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class PathParamSubresource {
	@GET
	@Path("{make}")
	@Produces(MediaType.APPLICATION_JSON)
	public String handleBasicPathParam(@PathParam(value = "make") String make) {

		return make;
	}
}
