package name.abhijitsarkar.learning.webservices.jaxrs.content.provider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import name.abhijitsarkar.learning.webservices.jaxrs.content.domain.Car;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;

// A Java EE container can auto-detect and register classes annotated with @Provider; Servlet containers like
// Jetty can't and need these classes explicitly registered in the Application class. For a standalone client,
// the Provider only needs to be registered with the client framework.

@Provider
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class CarsUnmarshaler implements MessageBodyReader<List<Car>> {

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return ProviderUtil.isManageable(type, genericType, annotations,
				mediaType);
	}

	@Override
	public List<Car> readFrom(Class<List<Car>> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws WebApplicationException {

		try {
			if (mediaType.equals(MediaType.APPLICATION_XML_TYPE)) {
				return readXML(type, genericType, mediaType, entityStream);
			}

			return readJSON(entityStream);
		} catch (Exception e) {
			e.printStackTrace();

			throw new WebApplicationException(e);
		}
	}

	private List<Car> readXML(Class<List<Car>> type, Type genericType,
			MediaType mediaType, InputStream entityStream) throws Exception {
		Transformer tf = null;

		try {
			tf = TransformerFactory.newInstance().newTransformer();
		} catch (Exception e) {
			throw new WebApplicationException(
					"Can't unmarshal content for media type="
							+ mediaType.toString() + ", type=" + type
							+ ", genericType=" + genericType,
					Status.INTERNAL_SERVER_ERROR);
		}

		Source source = new StreamSource(entityStream);
		DOMResult result = new DOMResult();

		tf.transform(source, result);

		Node rootNode = result.getNode().getFirstChild();

		NodeList children = rootNode.getChildNodes();

		List<Car> cars = new ArrayList<Car>();

		int numChildren = children.getLength();

		Node child = null;
		Class<?> clazz = null;
		Car car = null;
		String classnameAttr = "classname";

		for (int i = 0; i < numChildren; i++) {
			child = children.item(i);

			clazz = Class.forName(child.getAttributes()
					.getNamedItem(classnameAttr).getNodeValue());

			car = (Car) clazz.newInstance();

			car.setName(child.getTextContent());

			cars.add(car);
		}

		return cars;
	}

	@SuppressWarnings("unchecked")
	private List<Car> readJSON(InputStream entityStream) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readValue(entityStream, List.class);
	}
}
