package name.abhijitsarkar.learning.webservices.jaxws.security.sym;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(portName = "CalculatorSym", name = "CalculatorSym", serviceName = "CalculatorSymService", targetNamespace = "http://abhijitsarkar.name/learning/webservices/jaxws/security/")
@HandlerChain(file = "jaxws-handler-chains.xml")
public class CalculatorSym {

	@WebMethod(operationName = "add")
	public int add(@WebParam(name = "i") final int i,
			@WebParam(name = "j") final int j) {
		return i + j;
	}
}
