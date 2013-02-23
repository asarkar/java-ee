package edu.certification.abhijitsarkar.ocewsd.jaxws.provider.bind;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "addResponse", namespace = "http://abhijitsarkar/certification/edu/ocewsd/jaxws/provider/")
public class AddResponse {
	public AddResponse() {

	}

	public AddResponse(int sum) {
		this.sum = sum;
	}

	@XmlElement(name = "sum")
	private int sum;

	public int getResult() {
		return sum;
	}
}
