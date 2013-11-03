package name.abhijitsarkar.learning.webservices.jaxws.aws.client;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

public class RequestTransformerTest {

	@Test
	public void testTransform() {
		RequestTransformer rt = new RequestTransformer();
		Result result = rt.transform(new StreamSource(
				RequestTransformerTest.class.getResourceAsStream(TEST_FILE)));
		Assert.assertTrue(
				"Expected javax.xml.transform.stream.StreamSource but got "
						+ result.getClass().getName(),
				result instanceof StreamResult);

		String xml = ((StreamResult) result).getWriter().toString();
		System.out.println(xml);

		String signature = getText(xml,
				"//*[local-name() = 'Signature']/text()");

		Assert.assertNotNull("Could not find signature in the request",
				signature);
		Assert.assertTrue("Could not find signature in the request", signature
				.trim().length() > 0);
	}

	@Ignore
	private String getText(String xml, String expression) {
		String result = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(new ByteArrayInputStream(xml
					.getBytes()));
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			result = (String) xpath.evaluate(expression, doc,
					XPathConstants.STRING);
		} catch (Exception e) {
			Assert.fail("Shouldn't be here");
		}
		return result;
	}

	private static final String TEST_FILE = "/aws-request.xml";
}
