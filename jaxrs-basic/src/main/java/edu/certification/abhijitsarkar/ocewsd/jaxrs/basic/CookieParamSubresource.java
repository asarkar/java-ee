package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class CookieParamSubresource {
	private static final Logger logger = AppLogger
			.getInstance(CookieParamSubresource.class);
	
	public CookieParamSubresource() {
		logger.debug("CookieParamSubresource invoked");
	}

	@GET
	@Produces(value = "text/plain")
	public String handleCookieParam(@CookieParam("make") String make) {
		logger.debug("Make: " + make);

		return make;
	}
}
