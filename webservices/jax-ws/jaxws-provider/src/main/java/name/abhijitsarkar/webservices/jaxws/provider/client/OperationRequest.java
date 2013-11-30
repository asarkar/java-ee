package name.abhijitsarkar.webservices.jaxws.provider.client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "operation", namespace = "http://abhijitsarkar.name/webservices/jaxws/provider/")
public class OperationRequest {
	public OperationRequest() {

	}

	public OperationRequest(int arg0, int arg1) {
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
