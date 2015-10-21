package name.abhijitsarkar.javaee.microservices.salon.common.domain;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.OffsetDateTime;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Converter
public class OffsetDateTimeConverter implements AttributeConverter<OffsetDateTime, String> {
	private static final TypeReference<Map<OffsetDateTime, String>> TYPE_REF = new TypeReference<Map<OffsetDateTime, String>>() {
	};

	private final ObjectMapper mapper;

	public OffsetDateTimeConverter() {
		/**
		 * Instead of using DateTimeFormatter, use Jackson for consistency
		 * because that's what Spring uses for HTTP message conversion.
		 */
		mapper = Jackson2ObjectMapperBuilder.json()
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
						DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.featuresToEnable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID).modules(new JavaTimeModule()).build();
	}

	@Override
	public String convertToDatabaseColumn(OffsetDateTime dateTime) {
		try {
			return mapper.writeValueAsString(dateTime).replaceAll("\"", "");
		} catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public OffsetDateTime convertToEntityAttribute(String dateTime) {
		try {
			Map<OffsetDateTime, String> value = mapper.readValue(map(dateTime, "dummy"), TYPE_REF);

			return value.keySet().iterator().next();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private String map(String key, String value) {
		return String.format("{\"%s\":\"%s\"}", key, value);
	}
}
