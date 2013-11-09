package edu.certification.abhijitsarkar.ocewsd.jaxrs.basic;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class QueryParamSubresource {
	private static final Logger logger = AppLogger.getInstance(QueryParamSubresource.class);
	
	public QueryParamSubresource() {
		logger.debug("QueryParamSubresource invoked");
	}

	@GET
	@Produces(value = "text/plain")
	public String handleQueryParam(@QueryParam("make") String make) {
		logger.debug("Make: " + make);
		
		return make;
	}
}
