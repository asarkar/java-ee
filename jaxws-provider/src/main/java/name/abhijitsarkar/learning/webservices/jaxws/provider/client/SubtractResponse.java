package name.abhijitsarkar.learning.webservices.jaxws.provider.client;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(name = "result", namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/provider/")
public class SubtractResponse {
	public SubtractResponse() {

	}

	public SubtractResponse(int difference) {
		this.difference = difference;
	}

	@XmlValue
	private int difference;

	public int getResult() {
		return difference;
	}
}
