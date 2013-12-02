package name.abhijitsarkar.webservices.jaxws.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DivisionByZero {
	private String message;

	public DivisionByZero() {

	}

	public DivisionByZero(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String value) {
		this.message = value;
	}
}
