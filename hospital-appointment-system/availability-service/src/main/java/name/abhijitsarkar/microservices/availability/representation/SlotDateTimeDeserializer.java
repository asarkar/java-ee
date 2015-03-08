package name.abhijitsarkar.microservices.availability.representation;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class SlotDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
	    throws IOException, JsonProcessingException {

	String text = jp.getText().trim();

	if (text.isEmpty()) {
	    return null;
	}

	return LocalDateTime.parse(text, ISO_LOCAL_DATE_TIME);
    }
}
