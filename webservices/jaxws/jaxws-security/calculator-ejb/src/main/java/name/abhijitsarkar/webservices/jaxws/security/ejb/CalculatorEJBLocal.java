package name.abhijitsarkar.webservices.jaxws.security.ejb;

import javax.jws.WebService;

@WebService
// EJB 3.1 spec section 4.9.7 says:
// "The same business interface cannot be both a local and a remote business
// interface of the bean"
public interface CalculatorEJBLocal extends CalculatorEJBRemote {
	//
	// @WebMethod(operationName = "add")
	// public int add(final int i, final int j);
}
