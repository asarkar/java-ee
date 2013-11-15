package name.abhijitsarkar.learning.webservices.jaxrs.content.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import name.abhijitsarkar.learning.webservices.jaxrs.content.domain.Car;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fasterxml.jackson.databind.ObjectMapper;

// A Java EE container can auto-detect and register classes annotated with @Provider; Servlet containers like
// Jetty can't and need these classes explicitly registered in the Application class 
@Provider
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class CarsMarshaler implements MessageBodyWriter<List<Car>> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return ProviderUtil.isManageable(type, genericType, annotations,
				mediaType);
	}

	@Override
	public long getSize(List<Car> cars, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(List<Car> cars, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws WebApplicationException {

		try {
			if (mediaType.equals(MediaType.APPLICATION_XML_TYPE)) {
				writeToXML(cars, type, genericType, mediaType, entityStream);
			} else {
				writeToJSON(cars, entityStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
	}

	private void writeToXML(List<Car> cars, Class<?> type, Type genericType,
			MediaType mediaType, OutputStream entityStream) throws Exception {
		Transformer tf = null;

		try {
			tf = TransformerFactory.newInstance().newTransformer();
		} catch (Exception e) {
			throw new WebApplicationException(
					"Can't marshal content for media type="
							+ mediaType.toString() + ", type=" + type
							+ ", genericType=" + genericType,
					Status.INTERNAL_SERVER_ERROR);
		}

		String namespace = "http://abhijitsarkar.name/learning/webservices/jaxrs/content/";
		String namespacePrefix = "jc";
		String carsLocalName = "cars";
		String aCarLocalName = "car";
		String classnameAttr = "classname";

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		Document doc = docFactory.newDocumentBuilder().newDocument();

		doc.setXmlStandalone(true);
		doc.setXmlVersion("1.0");

		Element rootElement = doc.createElementNS(namespace, namespacePrefix
				+ ":" + carsLocalName);
		Element childElement = null;

		for (Car aCar : cars) {
			childElement = doc.createElement(aCarLocalName);
			childElement.setAttribute(classnameAttr, aCar.getClass().getName());
			childElement.setTextContent(aCar.getName());

			rootElement.appendChild(childElement);
		}

		Source source = new DOMSource(rootElement);
		Result result = new StreamResult(entityStream);

		tf.transform(source, result);
	}

	private void writeToJSON(List<Car> cars, OutputStream entityStream)
			throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		// Enables adding classname to serialized stream so that deserializer
		// would know what type of concrete class to instantiate.
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		objectMapper.writeValue(entityStream, cars);
	}
}
