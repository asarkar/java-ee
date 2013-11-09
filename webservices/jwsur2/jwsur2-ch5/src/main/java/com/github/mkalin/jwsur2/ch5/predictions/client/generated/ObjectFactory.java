
package com.github.mkalin.jwsur2.ch5.predictions.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.github.mkalin.jwsur2.ch5.predictions.client.generated package. 
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

    private final static QName _VerbosityException_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "VerbosityException");
    private final static QName _GetOneResponse_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "getOneResponse");
    private final static QName _DeleteResponse_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "deleteResponse");
    private final static QName _GetAllResponse_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "getAllResponse");
    private final static QName _Create_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "create");
    private final static QName _GetOne_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "getOne");
    private final static QName _GetAll_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "getAll");
    private final static QName _CreateResponse_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "createResponse");
    private final static QName _Delete_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "delete");
    private final static QName _Edit_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "edit");
    private final static QName _EditResponse_QNAME = new QName("http://predictions.ch5.jwsur2.mkalin.github.com/", "editResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.mkalin.jwsur2.ch5.predictions.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllResponse }
     * 
     */
    public GetAllResponse createGetAllResponse() {
        return new GetAllResponse();
    }

    /**
     * Create an instance of {@link DeleteResponse }
     * 
     */
    public DeleteResponse createDeleteResponse() {
        return new DeleteResponse();
    }

    /**
     * Create an instance of {@link GetOneResponse }
     * 
     */
    public GetOneResponse createGetOneResponse() {
        return new GetOneResponse();
    }

    /**
     * Create an instance of {@link VerbosityException }
     * 
     */
    public VerbosityException createVerbosityException() {
        return new VerbosityException();
    }

    /**
     * Create an instance of {@link EditResponse }
     * 
     */
    public EditResponse createEditResponse() {
        return new EditResponse();
    }

    /**
     * Create an instance of {@link Edit }
     * 
     */
    public Edit createEdit() {
        return new Edit();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link CreateResponse }
     * 
     */
    public CreateResponse createCreateResponse() {
        return new CreateResponse();
    }

    /**
     * Create an instance of {@link GetAll }
     * 
     */
    public GetAll createGetAll() {
        return new GetAll();
    }

    /**
     * Create an instance of {@link GetOne }
     * 
     */
    public GetOne createGetOne() {
        return new GetOne();
    }

    /**
     * Create an instance of {@link Create }
     * 
     */
    public Create createCreate() {
        return new Create();
    }

    /**
     * Create an instance of {@link Prediction }
     * 
     */
    public Prediction createPrediction() {
        return new Prediction();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerbosityException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "VerbosityException")
    public JAXBElement<VerbosityException> createVerbosityException(VerbosityException value) {
        return new JAXBElement<VerbosityException>(_VerbosityException_QNAME, VerbosityException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "getOneResponse")
    public JAXBElement<GetOneResponse> createGetOneResponse(GetOneResponse value) {
        return new JAXBElement<GetOneResponse>(_GetOneResponse_QNAME, GetOneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "deleteResponse")
    public JAXBElement<DeleteResponse> createDeleteResponse(DeleteResponse value) {
        return new JAXBElement<DeleteResponse>(_DeleteResponse_QNAME, DeleteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "getAllResponse")
    public JAXBElement<GetAllResponse> createGetAllResponse(GetAllResponse value) {
        return new JAXBElement<GetAllResponse>(_GetAllResponse_QNAME, GetAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Create }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "create")
    public JAXBElement<Create> createCreate(Create value) {
        return new JAXBElement<Create>(_Create_QNAME, Create.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOne }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "getOne")
    public JAXBElement<GetOne> createGetOne(GetOne value) {
        return new JAXBElement<GetOne>(_GetOne_QNAME, GetOne.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "getAll")
    public JAXBElement<GetAll> createGetAll(GetAll value) {
        return new JAXBElement<GetAll>(_GetAll_QNAME, GetAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "createResponse")
    public JAXBElement<CreateResponse> createCreateResponse(CreateResponse value) {
        return new JAXBElement<CreateResponse>(_CreateResponse_QNAME, CreateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "delete")
    public JAXBElement<Delete> createDelete(Delete value) {
        return new JAXBElement<Delete>(_Delete_QNAME, Delete.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Edit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "edit")
    public JAXBElement<Edit> createEdit(Edit value) {
        return new JAXBElement<Edit>(_Edit_QNAME, Edit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://predictions.ch5.jwsur2.mkalin.github.com/", name = "editResponse")
    public JAXBElement<EditResponse> createEditResponse(EditResponse value) {
        return new JAXBElement<EditResponse>(_EditResponse_QNAME, EditResponse.class, null, value);
    }

}
