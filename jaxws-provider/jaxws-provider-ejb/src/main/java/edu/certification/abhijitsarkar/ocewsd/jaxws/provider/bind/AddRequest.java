package edu.certification.abhijitsarkar.ocewsd.jaxws.provider.bind;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "add", namespace = "http://abhijitsarkar/certification/edu/ocewsd/jaxws/provider/")
public class AddRequest {
	public AddRequest() {

	}

	public AddRequest(int arg0, int arg1) {
		this.arg0 = arg0;
		this.arg1 = arg1;
	}

	@XmlElement
	private int arg0;
	@XmlElement
	private int arg1;

	public int getArg0() {
		return arg0;
	}

	public int getArg1() {
		return arg1;
	}
}
