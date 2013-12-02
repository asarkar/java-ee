package name.abhijitsarkar.webservices.jaxws.exception;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

@WebService
@HandlerChain(file = "jaxws-handler-chains.xml")
public class Calculator {
	public int multiply(int arg0, int arg1) {
		return (arg0 * arg1);
	}

	public int divide(int arg0, int arg1) throws DivisionByZeroException {
		if (arg1 == 0) {
			String errorMsg = "Divisor shouldn't be zero";

			DivisionByZero divisionByZero = new DivisionByZero(errorMsg);
			/*
			 * Mapped exception: If the client is a dynamic proxy type, then
			 * it'll see the in the form of the generated exception class. If
			 * the client is a dispatch type, it'll see the exception in the
			 * form of a ProtocolException subclass, which in this case is
			 * SOAPFaultException class.
			 */
			throw new DivisionByZeroException(errorMsg, divisionByZero);
		}

		if (arg0 < 0 || arg1 < 0) {
			/*
			 * Unmapped exception: Regardless of the type of client, it'll see
			 * the exception in the form of a ProtocolException subclass, which
			 * in this case is SOAPFaultException class.
			 */
			throw new WebServiceException(
					"Dividend or divisor shouldn't be negative");
		}

		return (arg0 / arg1);
	}
}
