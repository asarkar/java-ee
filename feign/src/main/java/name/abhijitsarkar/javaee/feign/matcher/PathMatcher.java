package name.abhijitsarkar.javaee.feign.matcher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import name.abhijitsarkar.javaee.feign.model.RequestProperties;
import org.springframework.util.AntPathMatcher;

import java.util.function.Predicate;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public class PathMatcher implements Predicate<RequestProperties> {
    @NonNull
    private final String path;

    private final org.springframework.util.PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean test(RequestProperties requestProperties) {
        return pathMatcher.match(requestProperties.getPath(), path);
    }
}
