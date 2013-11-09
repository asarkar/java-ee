package edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container.client;

import java.util.Arrays;
import java.util.TimeZone;

import javax.xml.ws.WebServiceRef;

import edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container.client.generated.TimeService;
import edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container.client.generated.UnsupportedTimeZoneException;

public class AppClient {

	public static void main(String... args) throws UnsupportedTimeZoneException {
		final String[] tenAvailableIDs = Arrays.copyOfRange(
				TimeZone.getAvailableIDs(), 0, 10);

		for (final String id : tenAvailableIDs) {
			System.out.println("[" + id + ", "
					+ service.getTimeServiceServletContainerPort().getTime(id)
					+ "]");
		}

		/* Should throw an exception */
		service.getTimeServiceServletContainerPort().getTime("");
	}

	@WebServiceRef(wsdlLocation = "http://abhijit-lenovo:8080/jaxws-servlet-container/TimeServiceServletContainer?wsdl")
	private static TimeService service;
}
