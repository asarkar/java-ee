package com.github.mkalin.jwsur2.ch3.cds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CatalogTest {
	private static String xml;

	@BeforeClass
	public static void oneTimeSetUp() throws IOException {
		InputStream is = CatalogTest.class.getResourceAsStream("/catalog.xml");

		Assert.assertNotNull("InputStream should not be null", is);

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		StringBuilder buffer = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}

		reader.close();

		xml = buffer.toString();

		Assert.assertNotNull("XML should not be null", xml);
	}

	@Test
	public void testCatalogXML2Java() throws JAXBException, IOException {
		Catalog catalog = unmarshal(xml);

		List<CD> cds = catalog.getCds();

		Assert.assertEquals("Wrong number of CDs", 3, cds.size());

		CD bobDylanCD = new CD();
		bobDylanCD.setArtist("Bob Dylan");
		bobDylanCD.setTitle("Empire Burlesque");
		bobDylanCD.setYear(1985);

		Assert.assertTrue("Bob Dylan CD should be found in the catalog",
				cds.contains(bobDylanCD));

		bobDylanCD.setYear(1986);

		Assert.assertFalse(
				"Imaginary Bob Dylan CD should not be found in the catalog",
				cds.contains(bobDylanCD));
	}

	@Test
	public void testCatalogJava2JSON() throws JAXBException, IOException {
		Catalog catalog = unmarshal(xml);

		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(catalog);

		// 100 is an arbitrary number but we control the test file so we know
		Assert.assertTrue("JSON catalog should not be null or empty",
				json != null && json.trim().length() > 100);
	}

	private Catalog unmarshal(String xml) throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		Reader reader = new StringReader(xml);
		Catalog catalog = (Catalog) unmarshaller.unmarshal(reader);

		reader.close();

		return catalog;
	}
}
