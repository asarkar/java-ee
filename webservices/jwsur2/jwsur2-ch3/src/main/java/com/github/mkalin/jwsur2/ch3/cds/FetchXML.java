package com.github.mkalin.jwsur2.ch3.cds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FetchXML {
	public void setJson(String json) {
	}

	public String getJson() {
		// JSONObject json = null;
		String json = null;
		try {
			// Fetch the XML document from the W3C site.
			String xml = "";
			URL url = new URL("http://www.w3schools.com/xml/cd_catalog.xml");
			URLConnection conn = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			// Read the document records.
			String line = null;
			while ((line = in.readLine()) != null)
				xml += line;
			in.close();

			// Clean up the XML.
			// xml = xml.replace("'", "");

			// Transform the XML document into a JSON object,
			// in this case an array of song objects.
			json = xmlToJSON(xml);
			// json = XML.toJSONObject(xml.toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Returning JSON: " + json);
		return json; // JSON document
	}

	private String xmlToJSON(String xml) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		Reader reader = new StringReader(xml);
		Catalog catalog = (Catalog) unmarshaller.unmarshal(reader);

		reader.close();

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(catalog);
	}
}
