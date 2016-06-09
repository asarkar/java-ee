package name.abhijitsarkar.javaee.feign.matcher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import name.abhijitsarkar.javaee.feign.model.RequestProperties;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author Abhijit Sarkar
 */
@RequiredArgsConstructor
public abstract class PairMatcher implements Predicate<RequestProperties> {
    @NonNull
    private final Map<String, String> pairs;

    @Override
    public boolean test(RequestProperties requestProperties) {
        Map<String, String> pairs = getPairs(requestProperties);

        if (isEmpty(pairs)) {
            return true;
        }

        return pairs.entrySet().stream()
                .allMatch(e -> {
                    String key = e.getKey();
                    String val1 = e.getValue();
                    String val2 = this.pairs.get(key);

                    return (Objects.equals(val1, val2))
                            || (val1 != null && val2 != null && matches(val1, val2));
                });
    }

    protected abstract Map<String, String> getPairs(RequestProperties requestProperties);

    protected abstract boolean matches(String val1, String val2);
}
