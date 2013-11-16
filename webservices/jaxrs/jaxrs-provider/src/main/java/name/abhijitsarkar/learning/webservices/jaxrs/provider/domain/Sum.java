package name.abhijitsarkar.learning.webservices.jaxrs.provider.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "sum", namespace = "http://abhijitsarkar.name/learning/webservices/jaxrs/provider/")
public class Sum {
	public Sum() {

	}

	public Sum(int sum) {
		this.sum = sum;
	}

	@XmlValue
	private int sum;

	public int getSum() {
		return sum;
	}
}
