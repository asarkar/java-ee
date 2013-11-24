package name.abhijitsarkar.webservices.jaxrs.provider.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import name.abhijitsarkar.webservices.jaxrs.provider.domain.Sum;

@Path("calc")
public class Calculator {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Sum add(@QueryParam("arg0") int arg0, @QueryParam("arg1") int arg1) {
		return new Sum(arg0 + arg1);
	}
}
