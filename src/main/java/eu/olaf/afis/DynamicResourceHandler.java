package eu.olaf.afis;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.*;
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
import java.util.zip.CRC32;

/**
 * Created by rguru on 14/08/2017.
 */
@Component
public class DynamicResourceHandler {

    @Value( "${dynamicresources.harlocpath}" )
    private String harlocation;

    private Map<Long,HarResponse> resourceMap;

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

                        HarRequest req = x.getRequest();

                        if (req != null) {

                            String method = req.getMethod().name();

                            if (method != null) {

                            URL url = null;
                            try {
                                url = new URL(req.getUrl());

                                StringBuffer ukey = new StringBuffer();

                                ukey.append(url.getPath()).append(url.getQuery()).append(method);

                                String pdata  = req.getPostData()!=null ? req.getPostData().getText():"";


                                resourceMap.put(calculateCRC(url.getPath(),url.getQuery(),method,pdata), x.getResponse());

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }






                            } else

                            System.out.println("METHOD not specified for request " + req.getUrl());
                    } else

                    System.out.println("Request object cannpt be null for " + x.getPageref());

                });

            } catch (HarReaderException e) {
                e.printStackTrace();
            }
            System.out.println(har.getLog().getCreator().getName());
        });


    }


    public Map<Long, HarResponse> getResourceMap() {
        return  resourceMap;
    }

    public Long calculateCRC(String path, String query, String method, String postdata) {

        StringBuffer ukey = new StringBuffer();
        ukey.append(path).append(query).append(method);
        if ("POST".equalsIgnoreCase(method)) {
            ukey.append(postdata);
        }

        CRC32 crc32 = new CRC32();
        crc32.update(ukey.toString().getBytes());

        return crc32.getValue();



    }
}
