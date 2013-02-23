package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class FormParamSubresource {
	private static final Logger logger = AppLogger
			.getInstance(FormParamSubresource.class);
	
	public FormParamSubresource() {
		logger.debug("FormParamSubresource invoked");
	}

	/* Seems like forms must be POSTed */
	@POST
	@Produces(value = "text/plain")
	public String handleFormParam(@FormParam("make") String make) {
		logger.debug("Make: " + make);

		return make;
	}
}
