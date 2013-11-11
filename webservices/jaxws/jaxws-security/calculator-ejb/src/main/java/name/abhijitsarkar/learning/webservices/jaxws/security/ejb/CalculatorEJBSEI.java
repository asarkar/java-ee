package name.abhijitsarkar.learning.webservices.jaxws.security.ejb;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface CalculatorEJBSEI {

	@WebMethod(operationName = "add")
	public int add(final int i, final int j);
}
