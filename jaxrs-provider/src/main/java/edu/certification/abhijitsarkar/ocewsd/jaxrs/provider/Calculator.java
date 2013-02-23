package edu.certification.abhijitsarkar.ocewsd.jaxrs.provider;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;

import org.apache.log4j.Logger;

import edu.certification.abhijitsarkar.ocewsd.jaxrs.provider.xml.bind.AddRequest;
import edu.certification.abhijitsarkar.ocewsd.jaxrs.provider.xml.bind.AddResponse;
import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

@WebServiceProvider
@ServiceMode(Mode.PAYLOAD)
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class Calculator implements Provider<Source> {
	@Resource
	private WebServiceContext ctx;
	private static final Logger logger = AppLogger
			.getInstance(Calculator.class);

	// Expects
	// <ns:add
	// xmlns:ns="http://abhijitsarkar.certification.edu.ocewsd.jaxws.provider">
	// <arg0>i</arg0>
	// <arg1>j</arg1>
	// </ns:add>
	// Returns
	// <ns:addResponse
	// xmlns:ns="http://abhijitsarkar.certification.edu.ocewsd.jaxws.provider">
	// <return>i + j</return>
	// </ns:addResponse>
	@Override
	public Source invoke(Source src) {
		if (!isMethodAllowed()) {
			throw new HTTPException(405); // Method not allowed
		}

		try {
			JAXBContext context = JAXBContext.newInstance(AddRequest.class,
					AddResponse.class);
			
			logger.debug("Source class: " + src.getClass());

			AddRequest request = context.createUnmarshaller()
					.unmarshal(src, AddRequest.class).getValue();

			AddResponse response = new AddResponse(request.getArg0()
					+ request.getArg1());

			Writer writer = new StringWriter();

			context.createMarshaller().marshal(response, writer);

			String str = writer.toString();

			writer.close();

			return new StreamSource(new StringReader(str));
		} catch (Exception e) {
			e.printStackTrace();
			throw new HTTPException(500); // Internal server error
		}
	}

	private boolean isMethodAllowed() {
		MessageContext msgCtx = ctx.getMessageContext();
		String httpMethod = (String) msgCtx
				.get(MessageContext.HTTP_REQUEST_METHOD);
		httpMethod = httpMethod.trim().toUpperCase();
		
		return "POST".equals(httpMethod);
	}
}
