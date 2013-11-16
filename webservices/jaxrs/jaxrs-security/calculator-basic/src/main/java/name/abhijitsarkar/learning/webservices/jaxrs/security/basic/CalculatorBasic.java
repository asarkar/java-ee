package name.abhijitsarkar.learning.webservices.jaxrs.security.basic;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CalculatorBasic {

	@GET
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int add(@QueryParam("arg0") int arg0, @QueryParam("arg1") int arg1) {
		return arg0 + arg1;
	}
}
