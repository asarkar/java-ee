package name.abhijitsarkar.webservices.jaxrs.subresource;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class MatrixParamSubresource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{model}/{year}")
	public String handleMatrixParam(@MatrixParam("color") String color) {

		return color;
	}
}
