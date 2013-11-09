package name.abhijitsarkar.learning.webservices.jaxws.security.ut;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(portName = "CalculatorUT", name = "CalculatorUT", serviceName = "CalculatorUTService", targetNamespace = "http://abhijitsarkar.name/learning/webservices/jaxws/security/CalculatorUT/")
@HandlerChain(file = "jaxws-handler-chains.xml")
public class CalculatorUT {

	@WebMethod(operationName = "add")
	public int add(@WebParam(name = "i") final int i,
			@WebParam(name = "j") final int j) {
		return i + j;
	}
}
