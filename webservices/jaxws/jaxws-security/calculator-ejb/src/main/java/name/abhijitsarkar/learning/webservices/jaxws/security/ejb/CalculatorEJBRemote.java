package name.abhijitsarkar.learning.webservices.jaxws.security.ejb;

import javax.jws.WebService;

@WebService
public interface CalculatorEJBRemote {

	public int add(final int i, final int j);
}
