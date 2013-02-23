package edu.certification.abhijitsarkar.ocewsd.jaxrs.provider.client;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

import edu.certification.abhijitsarkar.ocewsd.jaxrs.provider.client.bind.AddRequest;
import edu.certification.abhijitsarkar.ocewsd.jaxrs.provider.client.bind.AddResponse;

public class CalculatorClient {

	public CalculatorClient() {
		cc = new DefaultClientConfig();
		c = Client.create(cc);
		c.addFilter(new LoggingFilter(System.out));
		r = c.resource(RESOURCE_URI);
	}

	public int add(int arg0, int arg1) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(AddRequest.class,
				AddResponse.class);

		Writer writer = new StringWriter();

		context.createMarshaller().marshal(new AddRequest(1, 1), writer);

		Source src = r.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(StreamSource.class, writer.toString());

		writer.close();

		AddResponse resp = context.createUnmarshaller()
				.unmarshal(src, AddResponse.class).getValue();

		return resp.getResult();
	}

	private final ClientConfig cc;
	private final Client c;
	private final WebResource r;
	private static final String RESOURCE_URI = "http://localhost:8080/jaxrs-provider/CalculatorService";
}
