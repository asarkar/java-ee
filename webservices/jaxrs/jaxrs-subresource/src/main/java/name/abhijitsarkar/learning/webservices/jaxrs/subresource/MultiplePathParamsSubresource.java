package name.abhijitsarkar.learning.webservices.jaxrs.subresource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class MultiplePathParamsSubresource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{make}-{model}-{year}")
	public String handleMultiplePathParams(@PathParam("make") String make,
			@PathParam("model") String model,
			@PathParam(value = "year") int year) {
		StringBuilder response = new StringBuilder();
		response.append("{\"Make:\"").append(make).append("\",\"Model:\"")
				.append(model).append("\",Year:\"").append(year).append("\"}");

		return response.toString();
	}
}
