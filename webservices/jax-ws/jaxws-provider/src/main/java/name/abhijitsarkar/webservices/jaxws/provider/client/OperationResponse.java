package name.abhijitsarkar.webservices.jaxws.provider.client;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(name = "result", namespace = "http://abhijitsarkar.name/webservices/jaxws/provider/")
public class OperationResponse {
	public OperationResponse() {

	}

	public OperationResponse(int difference) {
		this.difference = difference;
	}

	@XmlValue
	private int difference;

	public int getResult() {
		return difference;
	}
}
