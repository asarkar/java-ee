package name.abhijitsarkar.webservices.jaxrs.subresource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

public class ContextSubresource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{make}/{model}")
	public String handleContext(@Context UriInfo info) {
		List<PathSegment> pathSegments = info.getPathSegments();

		if (pathSegments != null && pathSegments.size() >= 3) {
			return pathSegments.get(3).getPath();
		}

		return info.getPath();
	}
}
