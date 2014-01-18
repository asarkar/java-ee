package name.abhijitsarkar.webservices.jaxws.instrumentation;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Calculator {

	@WebMethod()
	public int add(final int i, final int j) {
		System.out.println("Calculator service called...");
		return i + j;
	}
}
