
package name.abhijitsarkar.learning.webservices.jaxws.calculator;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the name.abhijitsarkar.learning.webservices.jaxws.calculator package. 
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

    private final static QName _AddResponse_QNAME = new QName("http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", "addResponse");
    private final static QName _SubtractRequest_QNAME = new QName("http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", "subtractRequest");
    private final static QName _SubtractResponse_QNAME = new QName("http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", "subtractResponse");
    private final static QName _AddRequest_QNAME = new QName("http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", "addRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: name.abhijitsarkar.learning.webservices.jaxws.calculator
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link SubtractResponse }
     * 
     */
    public SubtractResponse createSubtractResponse() {
        return new SubtractResponse();
    }

    /**
     * Create an instance of {@link SubtractRequest }
     * 
     */
    public SubtractRequest createSubtractRequest() {
        return new SubtractRequest();
    }

    /**
     * Create an instance of {@link AddRequest }
     * 
     */
    public AddRequest createAddRequest() {
        return new AddRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubtractRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", name = "subtractRequest")
    public JAXBElement<SubtractRequest> createSubtractRequest(SubtractRequest value) {
        return new JAXBElement<SubtractRequest>(_SubtractRequest_QNAME, SubtractRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubtractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", name = "subtractResponse")
    public JAXBElement<SubtractResponse> createSubtractResponse(SubtractResponse value) {
        return new JAXBElement<SubtractResponse>(_SubtractResponse_QNAME, SubtractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://abhijitsarkar.name/learning/webservices/jaxws/calculator/", name = "addRequest")
    public JAXBElement<AddRequest> createAddRequest(AddRequest value) {
        return new JAXBElement<AddRequest>(_AddRequest_QNAME, AddRequest.class, null, value);
    }

}
