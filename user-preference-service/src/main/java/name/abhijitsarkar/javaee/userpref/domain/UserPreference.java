package name.abhijitsarkar.javaee.userpref.domain;

import lombok.Value;

@Value
public class UserPreference {
    private final String name;
    private final String value;
    private final int serviceId;
}
