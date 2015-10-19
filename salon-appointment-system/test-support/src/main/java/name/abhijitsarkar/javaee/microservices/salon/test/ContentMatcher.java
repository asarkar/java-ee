package name.abhijitsarkar.javaee.microservices.salon.test;

import java.io.IOException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import com.fasterxml.jackson.databind.JsonNode;

public class ContentMatcher extends BaseMatcher<String> {
	private final Pair pair;

	public ContentMatcher(Pair pair) {
		this.pair = pair;
	}

	@Override
	public boolean matches(Object object) {
		if (!(object instanceof String)) {
			return false;
		}

		String content = (String) object;

		try {
			JsonNode tree = ObjectMapperFactory.getInstance().readTree(content);

			for (String path : pair.getPaths()) {
				tree = tree.path(path);
			}

			return tree.asText().matches(pair.getRegex());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void describeTo(Description desc) {
		// TODO Auto-generated method stub
	}
}
