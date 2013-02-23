package edu.certification.abhijitsarkar.ocewsd.jaxrs.json;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import edu.certification.abhijitsarkar.ocewsd.jaxrs.json.bind.Sum;

@Path("/calc")
public class Calculator {

	@POST
	@Produces({ MediaType.APPLICATION_XML })
	public Sum getSumAsXML(@FormParam("arg0") int arg0,
			@FormParam("arg1") int arg1) {
		return new Sum(arg0 + arg1);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Sum getSumAsJSON(@QueryParam("arg0") int arg0,
			@QueryParam("arg1") int arg1) {
		return new Sum(arg0 + arg1);
	}
}
