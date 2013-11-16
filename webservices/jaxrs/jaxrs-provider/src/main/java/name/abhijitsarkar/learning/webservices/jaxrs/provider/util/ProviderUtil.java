package name.abhijitsarkar.learning.webservices.jaxrs.provider.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.core.MediaType;

import name.abhijitsarkar.learning.webservices.jaxrs.provider.domain.Car;

public class ProviderUtil {
	public static boolean isManageable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		if (!(genericType instanceof ParameterizedType)) {
			return false;
		}

		ParameterizedType pType = (ParameterizedType) genericType;

		if (pType.getActualTypeArguments().length == 0) {
			return false;
		}

		Type t = pType.getActualTypeArguments()[0];

		if (t == null) {
			return false;
		}

		if (!MediaType.APPLICATION_XML_TYPE.equals(mediaType)) {
			return false;
		}

		return List.class.isAssignableFrom(type) && t.equals(Car.class);
	}
}
