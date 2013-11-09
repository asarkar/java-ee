package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class MultiplePathParamsSubresource {
	private static final Logger logger = AppLogger
			.getInstance(MultiplePathParamsSubresource.class);

	@GET
	@Produces(value = "text/plain")
	@Path("{make}-{model}-{year}")
	public String handleMultiplePathParams(@PathParam("make") String make,
			@PathParam("model") String model,
			@PathParam(value = "year") int year) {
		logger.debug("Make: " + make + ", Model: " + model + ", Year: " + year);

		return "Make: " + make + ", Model: " + model + ", Year: " + year;
	}
}
