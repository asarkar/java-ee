package name.abhijitsarkar.webservices.jaxrs.subresource;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class FormParamSubresource {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String handleFormParam(@FormParam("make") String make) {
		return make;
	}
}
