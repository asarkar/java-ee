package edu.certification.abhijitsarkar.ocewsd.jaxws.handler.client;

import edu.certification.abhijitsarkar.ocewsd.jaxws.handler.GreeterWs;
import edu.certification.abhijitsarkar.ocewsd.jaxws.handler.HelloWebService;

public class Client {
	public String sayHi(String name) {
		return port.sayHi(name);
	}

	public String sayHello(String name) {
		return port.sayHello(name);
	}

	private final GreeterWs service = new GreeterWs();
	private final HelloWebService port = service.getHelloWebServicePort();
}
