
package com.github.mkalin.jwsur2.ch5.predictions.client.generated;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.0
 * 
 */
@WebServiceClient(name = "PredictionsSOAPService", targetNamespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", wsdlLocation = "http://localhost:8080/ch5/predictions?wsdl")
public class PredictionsSOAPService
    extends Service
{

    private final static URL PREDICTIONSSOAPSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.github.mkalin.jwsur2.ch5.predictions.client.generated.PredictionsSOAPService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.github.mkalin.jwsur2.ch5.predictions.client.generated.PredictionsSOAPService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/ch5/predictions?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/ch5/predictions?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        PREDICTIONSSOAPSERVICE_WSDL_LOCATION = url;
    }

    public PredictionsSOAPService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PredictionsSOAPService() {
        super(PREDICTIONSSOAPSERVICE_WSDL_LOCATION, new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "PredictionsSOAPService"));
    }

    /**
     * 
     * @return
     *     returns PredictionsSOAP
     */
    @WebEndpoint(name = "PredictionsSOAPPort")
    public PredictionsSOAP getPredictionsSOAPPort() {
        return super.getPort(new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "PredictionsSOAPPort"), PredictionsSOAP.class);
    }

}
