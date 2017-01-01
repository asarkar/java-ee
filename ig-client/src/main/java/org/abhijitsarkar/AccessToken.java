package org.abhijitsarkar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken {
    @JsonProperty("access_token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
