package edu.certification.abhijitsarkar.ocewsd.martinkalin.ch01.ts;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class TimeClient {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:9876/ch01/ts?wsdl");
		// Qualified name of the service:
		// 1st arg is the service URI
		// 2nd is the service name published in the WSDL
		QName qname = new QName(
				"http://ts.ch01.martinkalin.ocewsd.abhijitsarkar.certification.edu/",
				"TimeServerImplService");
		// Create, in effect, a factory for the service.
		Service service = Service.create(url, qname);
		// Extract the endpoint interface, the service "port".
		TimeServer eif = service.getPort(TimeServer.class);
		System.out.println(eif.getTimeAsString());
		System.out.println(eif.getTimeAsElapsed());
	}
}
