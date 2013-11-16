package name.abhijitsarkar.learning.webservices.jaxrs.provider.client;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

//A Java EE container can scan and register classes annotated with @Provider or they can be registered with the 
//Application class; For a standalone client, the Provider needs to be registered with the client framework.

//@Provider
public class OriginalAcceptHdrRestorer implements ReaderInterceptor {

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext context)
			throws IOException, WebApplicationException {

		Object obj = context.proceed();

		MultivaluedMap<String, String> responseHdrs = context.getHeaders();

		List<String> originalAcceptHdr = responseHdrs.get("Original-Accept");

		// If header is not null, means the original accept header was modified
		if (originalAcceptHdr != null) {

			responseHdrs.put("Accept", originalAcceptHdr);

			responseHdrs.remove("Original-Accept");
		}

		return obj;
	}
}
