package name.abhijitsarkar.javaee.feign.model;

import lombok.Data;

import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
@Data
public class ResponseProperties {
    private int status;
    private Map<String, String> headers;
    private Body body;
}
