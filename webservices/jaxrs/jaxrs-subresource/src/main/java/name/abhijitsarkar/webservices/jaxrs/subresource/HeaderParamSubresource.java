package name.abhijitsarkar.webservices.jaxrs.subresource;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class HeaderParamSubresource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String handleHeaderParam(@HeaderParam("Make") String make) {

		return make;
	}
}
