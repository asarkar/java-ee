package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class MatrixParamSubresource {
	private static final Logger logger = AppLogger
			.getInstance(MatrixParamSubresource.class);
	
	public MatrixParamSubresource() {
		logger.debug("MatrixParamSubresource invoked");
	}

	@GET
	@Produces(value = "text/plain")
	@Path("{model}/{year}")
	public String handleMatrixParam(@MatrixParam("color") String color) {
		logger.debug("Color: " + color);

		return color;
	}
}
