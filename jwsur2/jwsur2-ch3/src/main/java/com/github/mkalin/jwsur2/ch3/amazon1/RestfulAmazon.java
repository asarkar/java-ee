package com.github.mkalin.jwsur2.ch3.amazon1;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RestfulAmazon {
	private static final String endpoint = "ecs.amazonaws.com";
	private static final String itemId = "0545010225"; // Harry Potter

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("RestfulAmazon <accessKeyId> <secretKey>");
			return;
		}
		new RestfulAmazon().lookupStuff(args[0].trim(), args[1].trim());
	}

	private void lookupStuff(String accessKeyId, String secretKey) {
		RequestHelper helper = new RequestHelper(endpoint, accessKeyId,
				secretKey);
		String requestUrl = null;

		// Store query string params in a hash.
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Version", "2009-03-31");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", itemId);
		params.put("ResponseGroup", "Small");
		params.put("AssociateTag", "kalin"); // any string should do

		requestUrl = helper.sign(params);
		String response = requestAmazon(requestUrl);

		// The string "null" is returned before the XML document.
		String noNullResponse = response.replaceFirst("null", "");
		System.out.println("Raw xml:\n" + noNullResponse);
		System.out.println("Author: " + getAuthor(noNullResponse));
	}

	private String requestAmazon(String stringUrl) {
		HttpGet httpget = new HttpGet(stringUrl);

		// Execute HTTP request
		System.out.println("executing request " + httpget.getURI());

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		BufferedReader reader = null;
		StringBuilder buffer = null;

		try {
			response = httpclient.execute(httpget);
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			System.out.println("----------------------------------------");

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to bother about connection release
			if (entity != null) {
				InputStream instream = entity.getContent();
				reader = new BufferedReader(new InputStreamReader(instream));
				String chunk = null;
				buffer = new StringBuilder();

				while ((chunk = reader.readLine()) != null) {
					buffer.append(chunk);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				response.close();
				httpclient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// String response = null;
		// try {
		// URL url = new URL(stringUrl);
		// URLConnection conn = url.openConnection();
		// conn.setDoInput(true);
		//
		// BufferedReader in = new BufferedReader(new InputStreamReader(
		// conn.getInputStream()));
		// String chunk = null;
		// while ((chunk = in.readLine()) != null)
		// response += chunk;
		// in.close();
		// } catch (Exception e) {
		// throw new RuntimeException("Arrrg! " + e);
		// }

		return buffer.toString();
	}

	private String getAuthor(String xml) {
		String author = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();

			Document doc = builder.parse(bais);
			NodeList results = doc.getElementsByTagName("Author");
			for (int i = 0; i < results.getLength(); i++) {
				Element e = (Element) results.item(i);
				NodeList nodes = e.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node child = nodes.item(j);
					if (child.getNodeType() == Node.TEXT_NODE)
						author = child.getNodeValue();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Xml bad!", e);
		}
		return author;
	}
}
