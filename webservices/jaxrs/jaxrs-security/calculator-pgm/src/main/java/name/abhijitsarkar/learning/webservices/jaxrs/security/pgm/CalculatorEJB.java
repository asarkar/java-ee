package name.abhijitsarkar.learning.webservices.jaxrs.security.pgm;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import name.abhijitsarkar.learning.webservices.jaxrs.security.pgm.auth.MustBeCelebrityFeature.MustBeCelebrity;

// JAX-RS annotations are supported on local interfaces or no-interface beans of stateless session or singleton beans.
@Stateless
@Path("/")
public class CalculatorEJB {

	@GET
	@Path("add")
	@MustBeCelebrity
	public int add(@QueryParam("arg0") int arg0, @QueryParam("arg1") int arg1,
			@Context SecurityContext secCtx) {
		// SecurityContext is deliberately unused, it is just to show that we
		// can inject :)

		if (secCtx == null) {
			throw new WebApplicationException(
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return arg0 + arg1;
	}
}
