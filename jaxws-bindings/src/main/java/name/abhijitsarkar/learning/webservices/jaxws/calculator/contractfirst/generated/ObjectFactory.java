
package name.abhijitsarkar.learning.webservices.jaxws.calculator.contractfirst.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the name.abhijitsarkar.learning.webservices.jaxws.calculator.contractfirst.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddResponse_QNAME = new QName("http://abhijitsarkar.name/learning/webservices/jaxws/calculator-cf/", "addResponse");
    private final static QName _SubtractDefaultWrapped_QNAME = new QName("http://abhijitsarkar.name/learning/webservices/jaxws/calculator-cf/", "subtractDefaultWrapped");
    private final static QName _AddDefaultBareRequest_QNAME = new QName("http://abhijitsarkar.name/learning/webservices/jaxws/calculator-cf/", "addDefaultBareRequest");
    private final static QName _SubtractResponse_QNAME = new QName("http://abhijitsarkar.name/learning/webservices/jaxws/calculator-cf/", "subtractResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: name.abhijitsarkar.learning.webservices.jaxws.calculator.contractfirst.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SubtractResponse }
     * 
     */
    public SubtractResponse createSubtractResponse() {
        return new SubtractResponse();
    }

    /**
     * Create an instance of {@link AddDefaultBareRequest }
     * 
     */
    public AddDefaultBareRequest createAddDefaultBareRequest() {
        return new AddDefaultBareRequest();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link SubtractDefaultWrapped }
     * 
     */
    public SubtractDefaultWrapped createSubtractDefaultWrapped() {
        return new SubtractDefaultWrapped();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator-cf/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubtractDefaultWrapped }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator-cf/", name = "subtractDefaultWrapped")
    public JAXBElement<SubtractDefaultWrapped> createSubtractDefaultWrapped(SubtractDefaultWrapped value) {
        return new JAXBElement<SubtractDefaultWrapped>(_SubtractDefaultWrapped_QNAME, SubtractDefaultWrapped.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDefaultBareRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator-cf/", name = "addDefaultBareRequest")
    public JAXBElement<AddDefaultBareRequest> createAddDefaultBareRequest(AddDefaultBareRequest value) {
        return new JAXBElement<AddDefaultBareRequest>(_AddDefaultBareRequest_QNAME, AddDefaultBareRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubtractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator-cf/", name = "subtractResponse")
    public JAXBElement<SubtractResponse> createSubtractResponse(SubtractResponse value) {
        return new JAXBElement<SubtractResponse>(_SubtractResponse_QNAME, SubtractResponse.class, null, value);
    }

}
