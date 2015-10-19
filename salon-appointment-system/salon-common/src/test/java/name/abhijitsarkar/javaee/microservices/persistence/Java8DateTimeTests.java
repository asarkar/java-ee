package name.abhijitsarkar.javaee.microservices.persistence;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Java8DateTimeTests {
	private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
			.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
					DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.featuresToEnable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID).modules(new JavaTimeModule()).build();

	@Test
	public void testLocalDateTime() throws JsonProcessingException {
		LocalDateTime localDateTime = LocalDateTime.of(2015, 8, 15, 11, 40, 10, 100_000_000);

		System.out.println("Serialize LocalDateTime using ObjectMapper: "
				+ mapper.writeValueAsString(localDateTime).replaceAll("\"", ""));

		System.out.println("Serialize LocalDateTime using DateTimeFormatter: "
				+ DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime));
	}

	@Test
	public void testOffsetDateTime() throws JsonProcessingException {
		OffsetDateTime offsetDateTime = OffsetDateTime.of(2015, 8, 15, 11, 40, 10, 100_000_000, ZoneOffset.ofHours(8));

		System.out.println("Serialize OffsetDateTime using ObjectMapper: "
				+ mapper.writeValueAsString(offsetDateTime).replaceAll("\"", ""));

		System.out.println("Serialize OffsetDateTime using DateTimeFormatter: "
				+ DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(offsetDateTime));
	}

	@Test
	public void testZonedDateTime() throws JsonProcessingException {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(2015, 8, 15, 11, 40, 10, 100_000_000,
				ZoneId.of("Asia/Shanghai"));

		System.out.println("Serialize ZonedDateTime using ObjectMapper: "
				+ mapper.writeValueAsString(zonedDateTime).replaceAll("\"", ""));

		System.out.println("Serialize ZonedDateTime using DateTimeFormatter: "
				+ DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));
	}
}
