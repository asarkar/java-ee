package name.abhijitsarkar.webservices.jaxws.instrumentation.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import name.abhijitsarkar.webservices.jaxws.instrumentation.config.AppConfig;

@XmlRootElement(name = "addResponse", namespace = AppConfig.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
public class AddResponse {
	public AddResponse() {

	}

	@XmlElement(name = "return")
	private int sum;

	public int getSum() {
		return sum;
	}
}