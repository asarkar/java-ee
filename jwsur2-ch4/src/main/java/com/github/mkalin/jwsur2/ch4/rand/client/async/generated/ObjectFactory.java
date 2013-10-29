
package com.github.mkalin.jwsur2.ch4.rand.client.async.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.github.mkalin.jwsur2.ch4.rand.client.async.NextNResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.mkalin.jwsur2.ch4.rand.client.async.generated package. 
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

    private final static QName _Next1Response_QNAME = new QName("http://rand.ch4.jwsur2.mkalin.github.com/", "next1Response");
    private final static QName _Next1_QNAME = new QName("http://rand.ch4.jwsur2.mkalin.github.com/", "next1");
    private final static QName _NextNResponse_QNAME = new QName("http://rand.ch4.jwsur2.mkalin.github.com/", "nextNResponse");
    private final static QName _NextN_QNAME = new QName("http://rand.ch4.jwsur2.mkalin.github.com/", "nextN");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.mkalin.jwsur2.ch4.rand.client.async.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Next1Response }
     * 
     */
    public Next1Response createNext1Response() {
        return new Next1Response();
    }

    /**
     * Create an instance of {@link Next1 }
     * 
     */
    public Next1 createNext1() {
        return new Next1();
    }

    /**
     * Create an instance of {@link NextN }
     * 
     */
    public NextN createNextN() {
        return new NextN();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Next1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rand.ch4.jwsur2.mkalin.github.com/", name = "next1Response")
    public JAXBElement<Next1Response> createNext1Response(Next1Response value) {
        return new JAXBElement<Next1Response>(_Next1Response_QNAME, Next1Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Next1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rand.ch4.jwsur2.mkalin.github.com/", name = "next1")
    public JAXBElement<Next1> createNext1(Next1 value) {
        return new JAXBElement<Next1>(_Next1_QNAME, Next1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NextNResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rand.ch4.jwsur2.mkalin.github.com/", name = "nextNResponse")
    public JAXBElement<NextNResponse> createNextNResponse(NextNResponse value) {
        return new JAXBElement<NextNResponse>(_NextNResponse_QNAME, NextNResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NextN }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rand.ch4.jwsur2.mkalin.github.com/", name = "nextN")
    public JAXBElement<NextN> createNextN(NextN value) {
        return new JAXBElement<NextN>(_NextN_QNAME, NextN.class, null, value);
    }

}
