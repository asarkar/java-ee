package edu.certification.abhijitsarkar.ocewsd.wssecurity.calculatorssl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 * @author Abhijit
 */
@WebService(serviceName = "CalculatorWS")
public class CalculatorWS {

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "add")
	public int add(@WebParam(name = "i") final int i,
			@WebParam(name = "j") final int j) {
		return i + j;
	}
}
