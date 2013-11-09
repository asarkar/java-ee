package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class HeaderParamSubresource {
	private static final Logger logger = AppLogger
			.getInstance(HeaderParamSubresource.class);
	
	public HeaderParamSubresource() {
		logger.debug("HeaderParamSubresource invoked");
	}

	@GET
	@Produces(value = "text/plain")
	public String handleHeaderParam(@HeaderParam("Make") String make) {
		logger.debug("Make: " + make);

		return make;
	}
}
