package eu.olaf.afis.model;

import de.sstoehr.harreader.model.HarResponse;
import org.springframework.http.HttpMethod;

/**
 * Created by rguru on 14/08/2017.
 */
public class DynamicResource {

    private String uri;

    private HttpMethod method;

    private HarResponse harResponse;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HarResponse getHarResponse() {
        return harResponse;
    }

    public void setHarResponse(HarResponse harResponse) {
        this.harResponse = harResponse;
    }

    public DynamicResource() {

    }

    public DynamicResource(String uri, HttpMethod method, HarResponse harEntry) {

        this.uri = uri;
        this.method = method;
        this.harResponse = harEntry;
    }
}
