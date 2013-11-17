package name.abhijitsarkar.learning.webservices.jaxrs.security.decl;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

// The application must register Jersey specific DynamicFeature 'RolesAllowedDynamicFeature' in the Application class
// to enable annotation-based security. Also, web.xml <security-constraint> element must be present; the web container
// checks for security before JAX-RS does and without a <security-constraint>, the proper security context is not set.
@Path("/")
// Either this or web.xml <security-role> must be present
@DeclareRoles("celebrity")
public class Calculator {

	@RolesAllowed("celebrity")
	@GET
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int add(@QueryParam("arg0") int arg0, @QueryParam("arg1") int arg1) {
		return arg0 + arg1;
	}

	@DenyAll
	@GET
	@Path("subtract")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int subtract(@QueryParam("arg0") int arg0,
			@QueryParam("arg1") int arg1) {
		return arg0 - arg1;
	}
}
