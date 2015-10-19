package name.abhijitsarkar.javaee.microservices.salon.appointment.service;

import java.time.OffsetDateTime;

import org.springframework.core.convert.converter.Converter;

import name.abhijitsarkar.javaee.microservices.salon.common.OffsetDateTimeConverter;

public class StringToOffsetDateTimeConverter implements Converter<String, OffsetDateTime> {
	private final OffsetDateTimeConverter converter = new OffsetDateTimeConverter();

	@Override
	public OffsetDateTime convert(String dateTime) {
		return converter.convertToEntityAttribute(dateTime);
	}
}
