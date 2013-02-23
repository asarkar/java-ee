package edu.certification.abhijitsarkar.ocewsd.jaxrs.provider.client.bind;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "addResponse", namespace = "http://abhijitsarkar.certification.edu.ocewsd.jaxrs.provider")
public class AddResponse {
	public AddResponse() {

	}

	public AddResponse(int result) {
		this.result = result;
	}

	@XmlElement(name = "return")
	private int result;

	public int getResult() {
		return result;
	}
}
