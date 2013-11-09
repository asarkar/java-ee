
package edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container package. 
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

    private final static QName _GetCurrentTime_QNAME = new QName("http://container.servlet.jaxws.ocewsd.abhijitsarkar.certification.edu/", "getCurrentTime");
    private final static QName _GetCurrentTimeResponse_QNAME = new QName("http://container.servlet.jaxws.ocewsd.abhijitsarkar.certification.edu/", "getCurrentTimeResponse");
    private final static QName _UnsupportedTimeZoneException_QNAME = new QName("http://container.servlet.jaxws.ocewsd.abhijitsarkar.certification.edu/", "UnsupportedTimeZoneException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCurrentTime }
     * 
     */
    public GetCurrentTime createGetCurrentTime() {
        return new GetCurrentTime();
    }

    /**
     * Create an instance of {@link GetCurrentTimeResponse }
     * 
     */
    public GetCurrentTimeResponse createGetCurrentTimeResponse() {
        return new GetCurrentTimeResponse();
    }

    /**
     * Create an instance of {@link UnsupportedTimeZoneException }
     * 
     */
    public UnsupportedTimeZoneException createUnsupportedTimeZoneException() {
        return new UnsupportedTimeZoneException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://container.servlet.jaxws.ocewsd.abhijitsarkar.certification.edu/", name = "getCurrentTime")
    public JAXBElement<GetCurrentTime> createGetCurrentTime(GetCurrentTime value) {
        return new JAXBElement<GetCurrentTime>(_GetCurrentTime_QNAME, GetCurrentTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentTimeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://container.servlet.jaxws.ocewsd.abhijitsarkar.certification.edu/", name = "getCurrentTimeResponse")
    public JAXBElement<GetCurrentTimeResponse> createGetCurrentTimeResponse(GetCurrentTimeResponse value) {
        return new JAXBElement<GetCurrentTimeResponse>(_GetCurrentTimeResponse_QNAME, GetCurrentTimeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsupportedTimeZoneException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://container.servlet.jaxws.ocewsd.abhijitsarkar.certification.edu/", name = "UnsupportedTimeZoneException")
    public JAXBElement<UnsupportedTimeZoneException> createUnsupportedTimeZoneException(UnsupportedTimeZoneException value) {
        return new JAXBElement<UnsupportedTimeZoneException>(_UnsupportedTimeZoneException_QNAME, UnsupportedTimeZoneException.class, null, value);
    }

}
