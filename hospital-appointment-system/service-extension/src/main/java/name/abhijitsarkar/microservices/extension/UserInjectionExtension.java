package name.abhijitsarkar.microservices.extension;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.WithAnnotations;

import name.abhijitsarkar.microservices.user.AbstractUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInjectionExtension implements Extension {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(UserInjectionExtension.class);

    /*
     * The first map is keyed by the type that some method produces and one or
     * more fields consume. The second map is keyed by the class that has fields
     * with @Consumes. Each such class may potentially have multiple fields
     * consuming different types, hence the list. Each item in the list if a
     * producer-consumer mapping. This code doesn't handle the case where one
     * field in a class may consume a type 'T' and another a collection of type
     * 'T'.
     */
    private Map<Class<?>, Map<Class<?>, Field>> consumerMap = new HashMap<>();
    private Map<Class<?>, Method> producerMap = new HashMap<>();

    /*
     * The @WithAnnotations annotation causes the container to deliver the
     * ProcessAnnotatedType events only for the types which contain the
     * specified annotation.
     */
    <X> void processAnnotatedType(@Observes @WithAnnotations({ Produces.class,
	    Consumes.class }) ProcessAnnotatedType<X> pat) {
	AnnotatedType<X> at = pat.getAnnotatedType();

	at.getFields()
		.stream()
		.filter(field -> field.isAnnotationPresent(Consumes.class))
		.forEach(
			field -> {
			    Class<? extends AbstractUser> injectionType = field
				    .getAnnotation(Consumes.class).value();

			    consumerMap.compute(
				    injectionType,
				    new FieldMappingFunction(field
					    .getJavaMember()));
			});

	at.getMethods()
		.stream()
		.filter(method -> method.isAnnotationPresent(Produces.class))
		.forEach(
			method -> {
			    Class<? extends AbstractUser> injectionType = method
				    .getAnnotation(Produces.class).value();

			    producerMap.compute(
				    injectionType,
				    new MethodMappingFunction(method
					    .getJavaMember()));
			});

    }

    <X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit) {
	Class<X> clazz = pit.getAnnotatedType().getJavaClass();

	List<SimpleImmutableEntry<Method, Field>> pc = Stream
		.of(clazz.getDeclaredFields())
		.filter(field -> field.isAnnotationPresent(Consumes.class))
		.map(field -> {
		    Class<?> injectionType = field
			    .getAnnotation(Consumes.class).value();

		    if (producerMap.get(injectionType) == null) {
			throw new NoSuchProducerException(
				"No producer found for type: " + injectionType);
		    }

		    LOGGER.info(
			    "Found @Consumer annotation on field: {} while processing injection target: {}.",
			    field.getName(), field.getDeclaringClass()
				    .getName());

		    return new SimpleImmutableEntry<>(producerMap
			    .get(injectionType), field);
		}).collect(toList());

	if (pc != null && !pc.isEmpty()) {
	    pit.setInjectionTarget(new UserInjectionTarget<X>(pit
		    .getInjectionTarget(), pc));
	}
    }

    private static class FieldMappingFunction implements
	    BiFunction<Class<?>, Map<Class<?>, Field>, Map<Class<?>, Field>> {
	private final Field f;

	private FieldMappingFunction(Field f) {
	    this.f = f;
	}

	@Override
	public Map<Class<?>, Field> apply(Class<?> injectionType,
		Map<Class<?>, Field> fieldMap) {
	    Map<Class<?>, Field> map = new HashMap<>();

	    if (fieldMap != null) {
		map.putAll(fieldMap);
	    }

	    Field field = map.compute(f.getDeclaringClass(), (injectionTarget,
		    consumerField) -> {
		return consumerField == null ? f : consumerField;
	    });

	    LOGGER.info(
		    "Found @Consumer annotation on field: {} while processing annotated type: {}.",
		    field.getName(), f.getDeclaringClass().getName());

	    return map;
	}
    }

    private static class MethodMappingFunction implements
	    BiFunction<Class<?>, Method, Method> {
	private final Method m;

	private MethodMappingFunction(Method m) {
	    this.m = m;
	}

	@Override
	public Method apply(Class<?> injectionType, Method producerMethod) {
	    if (producerMethod != null) {
		throw new TooManyProducersException(
			"More than one producer found for type: "
				+ injectionType.getName());
	    }

	    LOGGER.info(
		    "Found @Producer annotation on method: {} while processing annotated type: {}.",
		    m.getName(), m.getDeclaringClass().getName());

	    return m;
	}
    }
}
