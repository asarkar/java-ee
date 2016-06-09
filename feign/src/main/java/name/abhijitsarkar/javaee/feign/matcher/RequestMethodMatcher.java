package name.abhijitsarkar.javaee.feign.matcher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import name.abhijitsarkar.javaee.feign.model.RequestProperties;

import java.util.function.Predicate;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class RequestMethodMatcher implements Predicate<RequestProperties> {
    @NonNull
    private final String method;

    @Override
    public boolean test(RequestProperties requestProperties) {
        return method.matches(requestProperties.getMethod());
    }
}
