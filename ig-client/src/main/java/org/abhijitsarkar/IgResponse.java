package org.abhijitsarkar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * @author Abhijit Sarkar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IgResponse {
    @JsonProperty("data")
    private Collection<Media> media;

    public Collection<Media> getMedia() {
        return media;
    }

    public void setMedia(Collection<Media> media) {
        this.media = media;
    }
}
