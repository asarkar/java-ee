package name.abhijitsarkar.webservices.jaxws.exception;

import javax.xml.ws.WebFault;

@WebFault
public class DivisionByZeroException extends Exception {
	private static final long serialVersionUID = 2206743088406656528L;

	private DivisionByZero faultInfo;

	public DivisionByZeroException(String message, DivisionByZero faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public DivisionByZeroException(String message, DivisionByZero faultInfo,
			Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public DivisionByZero getFaultInfo() {
		return faultInfo;
	}
}
