package name.abhijitsarkar.javaee.feign.matcher;

import name.abhijitsarkar.javaee.feign.model.RequestProperties;

import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
public class QueryParamsMatcher extends PairMatcher {
    public QueryParamsMatcher(Map<String, String> pairs) {
        super(pairs);
    }

    @Override
    protected Map<String, String> getPairs(RequestProperties requestProperties) {
        return requestProperties.getQueries();
    }

    @Override
    protected boolean matches(String val1, String val2) {
        return val1.equals(val2);
    }
}
