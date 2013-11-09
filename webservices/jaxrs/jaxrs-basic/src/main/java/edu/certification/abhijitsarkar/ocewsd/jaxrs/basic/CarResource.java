package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

@Path("car")
public class CarResource {
	private static final Logger logger = AppLogger
			.getInstance(CarResource.class);
	
	@GET
	@Produces(value = "text/plain")
	@Path("ctx/{make}/{model}")
	public String handleContext(@Context UriInfo info) {
		logger.debug("Path: " + info.getPath() + ", Absolute Path: "
				+ info.getAbsolutePath() + ", Base Uri: " + info.getBaseUri());

		List<PathSegment> pathSegments = info.getPathSegments();

		if (pathSegments != null && pathSegments.size() >= 3) {
			return pathSegments.get(3).getPath();
		}

		return info.getPath();
	}

	@Path("subresource/{subresourceLocator}")
	public Object locateSubresource(
			@PathParam(value = "subresourceLocator") String subresourceLocator) {
		logger.debug("subresourceLocator: " + subresourceLocator);

		if (subresourceLocator == null
				|| subresourceLocator.trim().length() == 0
				|| subresourceLocator.equalsIgnoreCase("basicPathParam")) {
			logger.debug("Invoking BasicPathParamSubresource");
			return new BasicPathParamSubresource();
		} else if (subresourceLocator.equalsIgnoreCase("multiplePathParams")) {
			logger.debug("Invoking MultiplePathParamsSubresource");
			return new MultiplePathParamsSubresource();
		} else if (subresourceLocator.equalsIgnoreCase("multiplePathSegments")) {
			logger.debug("Invoking MultiplePathSegmentsSubresource");
			return new MultiplePathSegmentsSubresource();
		} else if (subresourceLocator.equalsIgnoreCase("matrixParam")) {
			logger.debug("Invoking MatrixParamSubresource");
			return new MatrixParamSubresource();
		} else if (subresourceLocator.equalsIgnoreCase("queryParam")) {
			logger.debug("Invoking QueryParamSubresource");
			return new QueryParamSubresource();
		} else if (subresourceLocator.equalsIgnoreCase("cookieParam")) {
			logger.debug("Invoking CookieParamSubresource");
			return new CookieParamSubresource();
		} else if (subresourceLocator.equalsIgnoreCase("headerParam")) {
			logger.debug("Invoking HeaderParamSubresource");
			return new HeaderParamSubresource();
		} else if (subresourceLocator.equalsIgnoreCase("formParam")) {
			logger.debug("Invoking FormParamSubresource");
			return new FormParamSubresource();
		}

		return new ContextSubresource();
	}
}