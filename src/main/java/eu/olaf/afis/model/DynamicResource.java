package eu.olaf.afis.model;

import de.sstoehr.harreader.model.HarResponse;

/**
 * Created by rguru on 14/08/2017.
 */
public class DynamicResource {

    public String getDataCheksum() {
        return dataCheksum;
    }

    public void setDataCheksum(String dataCheksum) {
        this.dataCheksum = dataCheksum;
    }

    private String dataCheksum;

    private HarResponse harResponse;

    public HarResponse getHarResponse() {
        return harResponse;
    }

    public void setHarResponse(HarResponse harResponse) {
        this.harResponse = harResponse;
    }

    public DynamicResource() {

    }



    public DynamicResource(String dataCheksum, HarResponse harResponse) {
        this.dataCheksum = dataCheksum;
        this.harResponse = harResponse;
    }
}
