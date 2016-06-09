package name.abhijitsarkar.javaee.feign.model;

import lombok.Data;

import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Abhijit Sarkar
 */
@Data
public class Body {
    private String raw;
    private String file;
    private String classpath;
    private String jsonPath;
    private String xPath;

    public Body() {
        long count = Stream.of(raw, file, classpath, jsonPath, xPath)
                .filter(Objects::nonNull)
                .count();

        isTrue(count <= 1, "Ambiguous request body declaration.");
    }

    @Override
    public String toString() {
        // TODO
        if (!isEmpty(raw)) {
            return raw;
        } else if (!isEmpty(file)) {

        } else if (!isEmpty(classpath)) {

        }

        return null;
    }
}
