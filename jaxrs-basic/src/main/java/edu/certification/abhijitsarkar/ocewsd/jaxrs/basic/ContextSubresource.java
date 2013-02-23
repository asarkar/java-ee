package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class ContextSubresource {
	private static final Logger logger = AppLogger
			.getInstance(ContextSubresource.class);

	public ContextSubresource() {
		logger.debug("ContextSubresource invoked");
	}

	@GET
	@Produces(value = "text/plain")
	@Path("{make}/{model}")
	public String handleContext(@Context UriInfo info) {
		logger.debug("Path: " + info.getPath() + ", Absolute Path: "
				+ info.getAbsolutePath() + ", Base Uri: " + info.getBaseUri());

		List<PathSegment> pathSegments = info.getPathSegments();

		if (pathSegments != null && pathSegments.size() >= 3) {
			return pathSegments.get(3).getPath();
		}

		return info.getPath();
	}
}
