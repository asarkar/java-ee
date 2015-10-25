package name.abhijitsarkar.javaee.microservices.salon.appointment.service;

import java.time.OffsetDateTime;
import java.util.Locale;

import org.springframework.format.Formatter;

import name.abhijitsarkar.javaee.microservices.salon.domain.OffsetDateTimeConverter;

public class OffsetDateTimeFormatter implements Formatter<OffsetDateTime> {
	private final OffsetDateTimeConverter converter = new OffsetDateTimeConverter();

	@Override
	public String print(OffsetDateTime dateTime, Locale locale) {
		return converter.format(dateTime);
	}

	@Override
	public OffsetDateTime parse(String dateTime, Locale locale) {
		return converter.parse(dateTime);
	}
}
