package name.abhijitsarkar.javaee.microservices.salon.test;

import java.util.List;

import lombok.Value;

@Value
public class Pair {
	private final List<String> paths;
	private final String regex;
}
