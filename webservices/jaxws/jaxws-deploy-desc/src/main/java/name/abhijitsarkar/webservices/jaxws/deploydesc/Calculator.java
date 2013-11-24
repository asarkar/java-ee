package name.abhijitsarkar.webservices.jaxws.deploydesc;

import javax.jws.WebService;

@WebService
public interface Calculator {
	public int add(final int i, final int j);
}
