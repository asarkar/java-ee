package edu.certification.abhijitsarkar.ocewsd.jaxws.addressing;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

@Addressing(enabled = true, required = true)
@WebService()
public class Calculator {

	@WebMethod()
	public int add(final int i, final int j) {
		return i + j;
	}
}
