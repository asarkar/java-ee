
package name.abhijitsarkar.webservices.jaxws.exception.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the name.abhijitsarkar.webservices.jaxws.exception.client.generated package. 
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

    private final static QName _MultiplyResponse_QNAME = new QName("http://exception.jaxws.webservices.abhijitsarkar.name/", "multiplyResponse");
    private final static QName _DivideResponse_QNAME = new QName("http://exception.jaxws.webservices.abhijitsarkar.name/", "divideResponse");
    private final static QName _DivisionByZeroException_QNAME = new QName("http://exception.jaxws.webservices.abhijitsarkar.name/", "DivisionByZeroException");
    private final static QName _Divide_QNAME = new QName("http://exception.jaxws.webservices.abhijitsarkar.name/", "divide");
    private final static QName _Multiply_QNAME = new QName("http://exception.jaxws.webservices.abhijitsarkar.name/", "multiply");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: name.abhijitsarkar.webservices.jaxws.exception.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MultiplyResponse }
     * 
     */
    public MultiplyResponse createMultiplyResponse() {
        return new MultiplyResponse();
    }

    /**
     * Create an instance of {@link Multiply }
     * 
     */
    public Multiply createMultiply() {
        return new Multiply();
    }

    /**
     * Create an instance of {@link Divide }
     * 
     */
    public Divide createDivide() {
        return new Divide();
    }

    /**
     * Create an instance of {@link DivisionByZero }
     * 
     */
    public DivisionByZero createDivisionByZero() {
        return new DivisionByZero();
    }

    /**
     * Create an instance of {@link DivideResponse }
     * 
     */
    public DivideResponse createDivideResponse() {
        return new DivideResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultiplyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.jaxws.webservices.abhijitsarkar.name/", name = "multiplyResponse")
    public JAXBElement<MultiplyResponse> createMultiplyResponse(MultiplyResponse value) {
        return new JAXBElement<MultiplyResponse>(_MultiplyResponse_QNAME, MultiplyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DivideResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.jaxws.webservices.abhijitsarkar.name/", name = "divideResponse")
    public JAXBElement<DivideResponse> createDivideResponse(DivideResponse value) {
        return new JAXBElement<DivideResponse>(_DivideResponse_QNAME, DivideResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DivisionByZero }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.jaxws.webservices.abhijitsarkar.name/", name = "DivisionByZeroException")
    public JAXBElement<DivisionByZero> createDivisionByZeroException(DivisionByZero value) {
        return new JAXBElement<DivisionByZero>(_DivisionByZeroException_QNAME, DivisionByZero.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Divide }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.jaxws.webservices.abhijitsarkar.name/", name = "divide")
    public JAXBElement<Divide> createDivide(Divide value) {
        return new JAXBElement<Divide>(_Divide_QNAME, Divide.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Multiply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.jaxws.webservices.abhijitsarkar.name/", name = "multiply")
    public JAXBElement<Multiply> createMultiply(Multiply value) {
        return new JAXBElement<Multiply>(_Multiply_QNAME, Multiply.class, null, value);
    }

}
