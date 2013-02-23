
package edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image.mtom;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image.mtom package. 
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

    private final static QName _GetImagesResponse_QNAME = new QName("http://mtom.image.ch03.martinkalin.ocewsd.abhijitsarkar.certification.edu/", "getImagesResponse");
    private final static QName _GetImages_QNAME = new QName("http://mtom.image.ch03.martinkalin.ocewsd.abhijitsarkar.certification.edu/", "getImages");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.certification.abhijitsarkar.ocewsd.martinkalin.ch03.image.mtom
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetImagesResponse }
     * 
     */
    public GetImagesResponse createGetImagesResponse() {
        return new GetImagesResponse();
    }

    /**
     * Create an instance of {@link GetImages }
     * 
     */
    public GetImages createGetImages() {
        return new GetImages();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImagesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mtom.image.ch03.martinkalin.ocewsd.abhijitsarkar.certification.edu/", name = "getImagesResponse")
    public JAXBElement<GetImagesResponse> createGetImagesResponse(GetImagesResponse value) {
        return new JAXBElement<GetImagesResponse>(_GetImagesResponse_QNAME, GetImagesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImages }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mtom.image.ch03.martinkalin.ocewsd.abhijitsarkar.certification.edu/", name = "getImages")
    public JAXBElement<GetImages> createGetImages(GetImages value) {
        return new JAXBElement<GetImages>(_GetImages_QNAME, GetImages.class, null, value);
    }

}
