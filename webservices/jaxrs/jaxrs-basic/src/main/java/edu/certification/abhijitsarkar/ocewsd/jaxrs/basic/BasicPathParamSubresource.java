package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class BasicPathParamSubresource {
	private static final Logger logger = AppLogger
			.getInstance(BasicPathParamSubresource.class);
	
	public BasicPathParamSubresource() {
		logger.debug("BasicPathParamSubresource invoked");
	}

	@GET
	@Path("{make}")
	@Produces(value = "text/plain")
	public String handleBasicPathParam(@PathParam(value = "make") String make) {
		logger.debug("Make: " + make);

		return make;
	}
}
