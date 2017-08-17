package eu.olaf.afis;

import de.sstoehr.harreader.model.HarContent;
import de.sstoehr.harreader.model.HarHeader;
import de.sstoehr.harreader.model.HarResponse;
import eu.olaf.afis.model.DynamicResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rguru on 14/08/2017.
 */
@Controller
public class WebController {

    @Autowired
    private DynamicResourceHandler handler;

    @RequestMapping(value = "/**")
    public ResponseEntity<byte[]> defaultPath(HttpServletRequest request) {



        Map<Long,HarResponse> rMap =  handler.getResourceMap();

        String path = request.getServletPath();
        String query = request.getQueryString();
        String method = request.getMethod();

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { e.printStackTrace(); }



        Long key = handler.calculateCRC(path,query,method,jb.toString());

        if(rMap.containsKey(key) ){
            HarResponse response = rMap.get(key);



            if (response == null) {
                return new ResponseEntity<>("No response found in HAR archive".getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<byte[]>(responseText(response),getHeaders(response),HttpStatus.valueOf(response.getStatus()));




        }

            return new ResponseEntity<>("Unmapped request".getBytes(), HttpStatus.OK);


    }


    private HttpHeaders getHeaders(HarResponse response) {
        List<HarHeader> headers = response.getHeaders();
        final HttpHeaders list = new HttpHeaders();
        if (headers!=null) {
            headers.forEach(harHeader -> {
                if(! harHeader.getName().equalsIgnoreCase("Content-Encoding"))
                    list.add(harHeader.getName(),harHeader.getValue());
            });
        }

        return list;
    }

    private byte[] responseText(HarResponse response) {

        HarContent content = response.getContent();

        if (content !=null) {
            String ctext = content.getText();
            if (ctext != null) {

                if (content.getEncoding() != null) {

                    switch (content.getEncoding()) {
                        case "base64":
                            return Base64.getDecoder().decode(ctext.getBytes());

                        default:
                            return ctext.getBytes();


                    }

                }

                return ctext.getBytes();
            }
        }
        return null;
    }

}
