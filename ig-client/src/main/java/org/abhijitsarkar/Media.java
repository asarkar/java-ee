package org.abhijitsarkar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;

/**
 * @author Abhijit Sarkar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {
    @JsonProperty("data")
    private Collection<Medium> media;

    public Collection<Medium> getMedia() {
        return media;
    }

    public void setMedia(Collection<Medium> media) {
        this.media = media;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Medium {
    private String link;
    private long likes;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Object> likes) {
        this.likes = Long.parseLong(likes.get("count").toString());
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Likes {
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
