package name.abhijitsarkar.webservices.jaxrs.subresource;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class CookieParamSubresource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String handleCookieParam(@CookieParam("make") String make) {
		return make;
	}
}
