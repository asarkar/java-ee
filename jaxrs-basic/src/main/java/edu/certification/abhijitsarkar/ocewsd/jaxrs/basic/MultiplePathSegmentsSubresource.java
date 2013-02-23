package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.PathSegment;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class MultiplePathSegmentsSubresource {
	private static final Logger logger = AppLogger
			.getInstance(MultiplePathSegmentsSubresource.class);
	
	public MultiplePathSegmentsSubresource() {
		logger.debug("MultiplePathSegmentsSubresource invoked");
	}

	@POST
	@Path("{make}/{model : .+}/year/{year}")
	public void handleMultiplePathSegments(
			@PathParam("model") List<PathSegment> car) {
		final int numberOfPathSegments = car.size();
		PathSegment pathSegment = null;

		logger.debug("There are " + numberOfPathSegments + " PathSegments");

		for (int i = 0; i < numberOfPathSegments; i++) {
			pathSegment = car.get(i);

			logger.debug("PathSegment #" + i + ": " + pathSegment.getPath());

			Set<Map.Entry<String, List<String>>> matrixParams = pathSegment
					.getMatrixParameters().entrySet();
			Iterator<Map.Entry<String, List<String>>> it = matrixParams
					.iterator();
			Map.Entry<String, List<String>> matrixParam = null;

			final int numberOfMatrixParams = matrixParams.size();

			logger.debug("There are " + numberOfMatrixParams
					+ " MatrixParams for PathSegment #" + i);

			for (int j = 0; j < numberOfMatrixParams; j++) {
				matrixParam = it.next();

				logger.debug("MatrixParam #" + i + ": {Key : "
						+ matrixParam.getKey() + ", Value: "
						+ matrixParam.getValue() + "}");
			}
		}
	}
}
