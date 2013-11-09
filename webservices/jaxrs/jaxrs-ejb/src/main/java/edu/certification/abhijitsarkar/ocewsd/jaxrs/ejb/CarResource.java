package edu.certification.abhijitsarkar.ocewsd.jaxrs.ejb;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

@Path("car")
@Stateless
public class CarResource {
	private static final Logger logger = AppLogger
			.getInstance(CarResource.class);

	@GET
	@Path("{make}")
	@Produces(value = "text/plain")
	public String handleBasicPathParam(@PathParam(value = "make") String make) {
		logger.debug("Make: " + make);

		return make;
	}
}
