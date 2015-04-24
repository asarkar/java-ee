package name.abhijitsarkar.javaee.microservices.availability.representation;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class SlotDateTimeSerializer extends
	JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jgen,
	    SerializerProvider provider) throws IOException,
	    JsonProcessingException {
	jgen.writeString(ISO_LOCAL_DATE_TIME.format(dateTime));
    }
}
