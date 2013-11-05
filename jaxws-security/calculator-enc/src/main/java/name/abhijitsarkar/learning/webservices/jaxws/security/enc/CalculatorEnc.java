package name.abhijitsarkar.learning.webservices.jaxws.security.enc;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(portName = "CalculatorEnc", name = "CalculatorEnc", serviceName = "CalculatorEncService", targetNamespace = "http://abhijitsarkar.name/learning/webservices/jaxws/security/")
@HandlerChain(file = "jaxws-handler-chains.xml")
public class CalculatorEnc {

	@WebMethod(operationName = "add")
	public int add(@WebParam(name = "i") final int i,
			@WebParam(name = "j") final int j) {
		return i + j;
	}
}
