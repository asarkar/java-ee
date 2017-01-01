package org.abhijitsarkar.ig.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;
import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Media {
    @JsonProperty("data")
    private Collection<Medium> media;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Medium {
        private String link;
        private long likes;

        public void setLikes(Map<String, Object> likes) {
            this.likes = Long.parseLong(likes.get("count").toString());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Likes {
        private long count;
    }
}

