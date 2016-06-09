package name.abhijitsarkar.javaee.feign.model;

import lombok.Data;

import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
@Data
public class RequestProperties {
    private String path;
    private String method;
    private Map<String, String> queries;
    private Map<String, String> headers;
    private Body body;
}
