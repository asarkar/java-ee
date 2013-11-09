
package com.github.mkalin.jwsur2.ch6.tempconvert.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.mkalin.jwsur2.ch6.tempconvert.client.generated package. 
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

    private final static QName _C2FResponse_QNAME = new QName("http://tempconvert.ch6.jwsur2.mkalin.github.com/", "c2fResponse");
    private final static QName _C2F_QNAME = new QName("http://tempconvert.ch6.jwsur2.mkalin.github.com/", "c2f");
    private final static QName _F2CResponse_QNAME = new QName("http://tempconvert.ch6.jwsur2.mkalin.github.com/", "f2cResponse");
    private final static QName _F2C_QNAME = new QName("http://tempconvert.ch6.jwsur2.mkalin.github.com/", "f2c");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.mkalin.jwsur2.ch6.tempconvert.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link F2C }
     * 
     */
    public F2C createF2C() {
        return new F2C();
    }

    /**
     * Create an instance of {@link F2CResponse }
     * 
     */
    public F2CResponse createF2CResponse() {
        return new F2CResponse();
    }

    /**
     * Create an instance of {@link C2F }
     * 
     */
    public C2F createC2F() {
        return new C2F();
    }

    /**
     * Create an instance of {@link C2FResponse }
     * 
     */
    public C2FResponse createC2FResponse() {
        return new C2FResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link C2FResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempconvert.ch6.jwsur2.mkalin.github.com/", name = "c2fResponse")
    public JAXBElement<C2FResponse> createC2FResponse(C2FResponse value) {
        return new JAXBElement<C2FResponse>(_C2FResponse_QNAME, C2FResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link C2F }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempconvert.ch6.jwsur2.mkalin.github.com/", name = "c2f")
    public JAXBElement<C2F> createC2F(C2F value) {
        return new JAXBElement<C2F>(_C2F_QNAME, C2F.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link F2CResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempconvert.ch6.jwsur2.mkalin.github.com/", name = "f2cResponse")
    public JAXBElement<F2CResponse> createF2CResponse(F2CResponse value) {
        return new JAXBElement<F2CResponse>(_F2CResponse_QNAME, F2CResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link F2C }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempconvert.ch6.jwsur2.mkalin.github.com/", name = "f2c")
    public JAXBElement<F2C> createF2C(F2C value) {
        return new JAXBElement<F2C>(_F2C_QNAME, F2C.class, null, value);
    }

}
