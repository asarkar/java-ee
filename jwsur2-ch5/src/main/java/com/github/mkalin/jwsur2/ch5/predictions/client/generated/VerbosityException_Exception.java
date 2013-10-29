
package com.github.mkalin.jwsur2.ch5.predictions.client.generated;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.0
 * 
 */
@WebFault(name = "VerbosityException", targetNamespace = "http://predictions.ch5.jwsur2.mkalin.github.com/")
public class VerbosityException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private VerbosityException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public VerbosityException_Exception(String message, VerbosityException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public VerbosityException_Exception(String message, VerbosityException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.github.mkalin.jwsur2.ch5.predictions.client.generated.VerbosityException
     */
    public VerbosityException getFaultInfo() {
        return faultInfo;
    }

}
