package name.abhijitsarkar.webservices.jaxws.deploydesc;

import javax.jws.WebService;

@WebService(endpointInterface = "name.abhijitsarkar.webservices.jaxws.deploydesc.Calculator")
public class CalculatorImpl implements Calculator {

	@Override
	public int add(final int i, final int j) {

		return i + j;
	}
}
