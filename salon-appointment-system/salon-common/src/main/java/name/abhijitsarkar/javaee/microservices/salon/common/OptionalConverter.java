package name.abhijitsarkar.javaee.microservices.salon.common;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OptionalConverter implements AttributeConverter<Optional<String>, String> {

	@Override
	public String convertToDatabaseColumn(Optional<String> opt) {
		return opt != null ? opt.orElse(null) : null;
	}

	@Override
	public Optional<String> convertToEntityAttribute(String opt) {
		return Optional.ofNullable(opt);
	}
}
