package com.github.mkalin.jwsur2.ch6.wssecurity;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@HandlerChain(file = "jaxws-handler-chains-echo.xml")
public class Echo {
	@WebMethod
	public String echo(String msg) {
		return "Echoing: " + msg;
	}
}
