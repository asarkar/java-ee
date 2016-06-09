package name.abhijitsarkar.javaee.feign.model;

import lombok.Data;

/**
 * @author Abhijit Sarkar
 */
@Data
public class FeignMapping {
    private RequestProperties request;
    private ResponseProperties response;
}
