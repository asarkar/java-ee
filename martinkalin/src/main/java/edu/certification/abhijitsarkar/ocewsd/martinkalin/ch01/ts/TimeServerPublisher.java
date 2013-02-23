package edu.certification.abhijitsarkar.ocewsd.martinkalin.ch01.ts;

import javax.xml.ws.Endpoint;

/**
 * This application publishes the web service whose SIB is
 * edu.certification.abhijitsarkar.ocewsd.martinkalin.ch01.ts.TimeServerImpl.
 * For now, the service is published at localhost, and at port number 9876, as
 * this port is likely available on any desktop machine. The publication path is
 * /ch01/ts, an arbitrary name.
 * 
 * The application runs indefinitely, awaiting service requests. It needs to be
 * terminated at the command prompt with control-C or the equivalent.
 * 
 * Once the applicatation is started, open a browser to the URL
 * http://localhost:9876/ch01/ts?wsdl
 * 
 * to view the service contract, the WSDL document. This is an easy test to
 * determine whether the service has deployed successfully. If the test
 * succeeds, a client then can be executed against the service.
 */

public class TimeServerPublisher {
	public static void main(String[] args) {
		final String PUBLICATION_URL = "http://localhost:9876/ch01/ts";
		// 1st argument is the publication URL
		// 2nd argument is an SIB instance
		System.out.println("Publishing time server at URL: " + PUBLICATION_URL);
		Endpoint.publish(PUBLICATION_URL, new TimeServerImpl());
	}
}
