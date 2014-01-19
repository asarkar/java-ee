package name.abhijitsarkar.webservices.jaxws.instrumentation.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import name.abhijitsarkar.webservices.jaxws.instrumentation.config.AppConfig;

@XmlRootElement(name = "add", namespace = AppConfig.NAMESPACE_URI)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "arg0", "arg1" })
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

	public void setArg0(int arg0) {
		this.arg0 = arg0;
	}

	public int getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}
}