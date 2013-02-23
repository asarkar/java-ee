package edu.certification.abhijitsarkar.ocewsd.jaxws.handler;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 * @author Abhijit
 */
@WebService(serviceName = "GreeterWs")
@HandlerChain(file = "handler-chain.xml")
public class HelloWebService {

	@WebMethod(operationName = "sayHi")
	public String operation(@WebParam(name = "name") String txt) {
		return "Hi " + txt + " !";
	}

	@WebMethod(operationName = "sayHello")
	public String sayHello(@WebParam(name = "s") final String s) {
		return "Hello " + s;
	}
}
