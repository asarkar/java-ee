package name.abhijitsarkar.javaee.feign.matcher;

import name.abhijitsarkar.javaee.feign.model.RequestProperties;

import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
public class HeadersMatcher extends PairMatcher {
    public HeadersMatcher(Map<String, String> pairs) {
        super(pairs);
    }

    @Override
    protected Map<String, String> getPairs(RequestProperties requestProperties) {
        return requestProperties.getHeaders();
    }

    @Override
    // TODO
    protected boolean matches(String val1, String val2) {
        return val1.equals(val2);
    }
}
