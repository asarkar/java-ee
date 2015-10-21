package name.abhijitsarkar.javaee.microservices.salon.common.domain;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Assert;
import org.junit.Test;

import name.abhijitsarkar.javaee.microservices.salon.common.domain.OffsetDateTimeConverter;

public class OffsetDateTimeConverterTest {
	private OffsetDateTimeConverter converter = new OffsetDateTimeConverter();
	
	@Test
	public void testSerialization() {
		OffsetDateTime dateTime = OffsetDateTime.of(LocalDateTime.of(2015, 01, 01, 11, 00), ZoneOffset.of("-08:30"));		
		String actual = converter.convertToDatabaseColumn(dateTime);
		
		Assert.assertEquals("2015-01-01T11:00:00-08:30", actual);
	}
	
	@Test
	public void testDeserialization() {
		OffsetDateTime dateTime = OffsetDateTime.of(LocalDateTime.of(2015, 01, 01, 11, 00), ZoneOffset.of("-08:00"));
		
		OffsetDateTime actual = converter.convertToEntityAttribute("2015-01-01T11:00-08:00");
		
		Assert.assertEquals(dateTime, actual);
	}
}
