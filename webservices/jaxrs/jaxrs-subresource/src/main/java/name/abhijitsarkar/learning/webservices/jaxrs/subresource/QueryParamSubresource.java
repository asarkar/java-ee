package name.abhijitsarkar.learning.webservices.jaxrs.subresource;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public class QueryParamSubresource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String handleQueryParam(@QueryParam("make") String make) {
		return make;
	}
}
