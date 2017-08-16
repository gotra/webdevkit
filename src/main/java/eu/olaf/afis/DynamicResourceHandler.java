package eu.olaf.afis;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarLog;
import eu.olaf.afis.model.DynamicResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by rguru on 14/08/2017.
 */
@Component
public class DynamicResourceHandler {

    @Value( "${dynamicresources.harlocpath}" )
    private String harlocation;

    private Map<String,DynamicResource> resourceMap;

    @PostConstruct
    public void init() throws Exception {

        resourceMap = new HashMap<>();
        Files.newDirectoryStream(Paths.get(harlocation),"*.har").forEach((Path k) -> {
            HarReader harReader = new HarReader();
            Har har = null;
            try {
                har = harReader.readFromFile(new File(k.toString()));
                HarLog harLog = har.getLog();
                harLog.getEntries().forEach(x -> {
                    try {
                        String uri =  new URL(x.getRequest().getUrl()).getPath();
                        HttpMethod method = HttpMethod.resolve(x.getRequest().getMethod().name());

                        resourceMap.put(uri,new DynamicResource(uri,method,x.getResponse()));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                });

            } catch (HarReaderException e) {
                e.printStackTrace();
            }
            System.out.println(har.getLog().getCreator().getName());
        });


    }


    public Map<String, DynamicResource> getResourceMap() {
        return  resourceMap;
    }
}
