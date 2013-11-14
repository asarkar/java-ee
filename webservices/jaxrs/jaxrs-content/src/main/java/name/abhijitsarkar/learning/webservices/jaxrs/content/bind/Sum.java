package name.abhijitsarkar.learning.webservices.jaxrs.content.bind;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "sum", namespace = "http://abhijitsarkar.name/learning/webservices/jaxrs/content/")
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
