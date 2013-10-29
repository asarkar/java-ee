
package com.github.mkalin.jwsur2.ch5.images.mtom.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.mkalin.jwsur2.ch5.images.mtom.client.generated package. 
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

    private final static QName _GetImageResponse_QNAME = new QName("http://mtom.images.ch5.jwsur2.mkalin.github.com/", "getImageResponse");
    private final static QName _GetImages_QNAME = new QName("http://mtom.images.ch5.jwsur2.mkalin.github.com/", "getImages");
    private final static QName _GetImage_QNAME = new QName("http://mtom.images.ch5.jwsur2.mkalin.github.com/", "getImage");
    private final static QName _GetImagesResponse_QNAME = new QName("http://mtom.images.ch5.jwsur2.mkalin.github.com/", "getImagesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.mkalin.jwsur2.ch5.images.mtom.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetImages }
     * 
     */
    public GetImages createGetImages() {
        return new GetImages();
    }

    /**
     * Create an instance of {@link GetImagesResponse }
     * 
     */
    public GetImagesResponse createGetImagesResponse() {
        return new GetImagesResponse();
    }

    /**
     * Create an instance of {@link GetImage }
     * 
     */
    public GetImage createGetImage() {
        return new GetImage();
    }

    /**
     * Create an instance of {@link GetImageResponse }
     * 
     */
    public GetImageResponse createGetImageResponse() {
        return new GetImageResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mtom.images.ch5.jwsur2.mkalin.github.com/", name = "getImageResponse")
    public JAXBElement<GetImageResponse> createGetImageResponse(GetImageResponse value) {
        return new JAXBElement<GetImageResponse>(_GetImageResponse_QNAME, GetImageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImages }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mtom.images.ch5.jwsur2.mkalin.github.com/", name = "getImages")
    public JAXBElement<GetImages> createGetImages(GetImages value) {
        return new JAXBElement<GetImages>(_GetImages_QNAME, GetImages.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mtom.images.ch5.jwsur2.mkalin.github.com/", name = "getImage")
    public JAXBElement<GetImage> createGetImage(GetImage value) {
        return new JAXBElement<GetImage>(_GetImage_QNAME, GetImage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetImagesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mtom.images.ch5.jwsur2.mkalin.github.com/", name = "getImagesResponse")
    public JAXBElement<GetImagesResponse> createGetImagesResponse(GetImagesResponse value) {
        return new JAXBElement<GetImagesResponse>(_GetImagesResponse_QNAME, GetImagesResponse.class, null, value);
    }

}
