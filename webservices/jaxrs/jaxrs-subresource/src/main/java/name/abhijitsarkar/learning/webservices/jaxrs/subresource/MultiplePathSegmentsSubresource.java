package name.abhijitsarkar.learning.webservices.jaxrs.subresource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

public class MultiplePathSegmentsSubresource {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{make}/{model : .+}/{year : \\d+}")
	public String handleMultiplePathSegments(@PathParam("make") String make,
			@PathParam("model") List<PathSegment> car,
			@PathParam("year") int year) {
		final int numberOfPathSegments = car.size();
		PathSegment pathSegment = null;

		List<Object> response = new ArrayList<Object>();
		response.add("Make:" + make);
		response.add("Year:" + year);
		response.add("Number of path segments:" + numberOfPathSegments);

		List<Object> pathSegmentList = new ArrayList<Object>();

		for (int i = 0; i < numberOfPathSegments; i++) {
			pathSegment = car.get(i);

			pathSegmentList.add("Path segment # " + i + ":"
					+ pathSegment.getPath());

			Set<Map.Entry<String, List<String>>> matrixParams = pathSegment
					.getMatrixParameters().entrySet();
			Iterator<Map.Entry<String, List<String>>> it = matrixParams
					.iterator();
			Map.Entry<String, List<String>> matrixParam = null;

			final int numberOfMatrixParams = matrixParams.size();

			pathSegmentList.add("Number of matrix params:"
					+ numberOfMatrixParams);

			Map<String, String> matrixParamMap = new HashMap<String, String>();
			for (int j = 0; j < numberOfMatrixParams; j++) {
				matrixParam = it.next();

				matrixParamMap.put("Matrix param #" + j + " [key, value]", "["
						+ matrixParam.getKey() + "," + matrixParam.getValue()
						+ "]");

			}
			pathSegmentList.add(matrixParamMap);
		}
		response.add(pathSegmentList);

		return response.toString();
	}
}
