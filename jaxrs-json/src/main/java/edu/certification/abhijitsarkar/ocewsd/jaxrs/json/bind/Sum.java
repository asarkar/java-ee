package edu.certification.abhijitsarkar.ocewsd.jaxrs.json.bind;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sum", namespace = "http://abhijitsarkar.certification.edu.ocewsd.jaxrs.json")
public class Sum {
	public Sum() {

	}
	
	public Sum(int sum) {
		this.sum = sum;
	}

	@XmlElement(name = "sum")
	private int sum;

	public int getSum() {
		return sum;
	}
}
