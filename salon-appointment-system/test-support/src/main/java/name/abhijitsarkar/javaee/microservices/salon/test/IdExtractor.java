package name.abhijitsarkar.javaee.microservices.salon.test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class IdExtractor implements Function<MvcResult, String> {
	@Override
	public String apply(MvcResult result) {
		try {
			String body = result.getResponse().getContentAsString();

			String uri = ObjectMapperFactory.getInstance().readTree(body).path("_links").path("self").path("href")
					.asText();

			return uri.substring(uri.lastIndexOf('/') + 1);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
