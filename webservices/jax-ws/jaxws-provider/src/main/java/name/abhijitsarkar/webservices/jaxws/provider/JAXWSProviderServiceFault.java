package name.abhijitsarkar.webservices.jaxws.provider;

import javax.xml.ws.WebServiceException;

public class JAXWSProviderServiceFault extends WebServiceException {

	private static final long serialVersionUID = -8545036499689110358L;

	public JAXWSProviderServiceFault() {
		super();
	}

	public JAXWSProviderServiceFault(String message, Throwable cause) {
		super(message, cause);
	}

	public JAXWSProviderServiceFault(String message) {
		super(message);
	}

	public JAXWSProviderServiceFault(Throwable cause) {
		super(cause);
	}
}
