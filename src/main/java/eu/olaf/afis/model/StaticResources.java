package eu.olaf.afis.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rguru on 10/08/2017.
 */
@Component
@ConfigurationProperties(prefix = "staticresouces")
public class StaticResources {

    private List<Resource> resourceList = new ArrayList<>();

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public StaticResources() {
    }
}
