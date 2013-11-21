package name.abhijitsarkar.webservices.jaxrs.subresource.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubresourceHttpURLConnectionClient {
	private static final String CAR_RESOURCE_URI = "http://localhost:8080/jaxrs-subresource/car";

	public static void main(String[] args) {
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		try {
			System.out.println("Path param HttpURLConnection request...");
			URL restURL = new URL(CAR_RESOURCE_URI
					+ "/subresource/basicPathParam/Mercedes");
			connection = (HttpURLConnection) restURL.openConnection();
			connection.setReadTimeout(5000);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoInput(true);
			connection.setDoOutput(false);

			// This seems unnecessary
			connection.connect();

			System.out
					.println("Response code: " + connection.getResponseCode());

			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();

			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
