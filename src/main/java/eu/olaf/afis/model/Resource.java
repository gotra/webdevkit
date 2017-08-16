package eu.olaf.afis.model;

import org.springframework.stereotype.Component;

/**
 * Created by rguru on 10/08/2017.
 */
@Component
public class Resource {

    private String uri;
    private String location;

    public Resource() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
