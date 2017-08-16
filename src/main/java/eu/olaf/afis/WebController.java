package eu.olaf.afis;

import eu.olaf.afis.model.DynamicResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by rguru on 14/08/2017.
 */
@Controller
public class WebController {

    @Autowired
    private DynamicResourceHandler handler;

    @RequestMapping(value = "/**")
    public ResponseEntity<String> defaultPath(HttpServletRequest request) {

        HashMap<String,DynamicResource> rMap = (HashMap<String, DynamicResource>) handler.getResourceMap();
        String key = request.getServletPath();
        if(rMap.containsKey(key) ){
            DynamicResource res = rMap.get(key);
            return new ResponseEntity<String>(res.getHarResponse().getContent().getText(),  HttpStatus.valueOf (res.getHarResponse().getStatus()));
        }
        else {
            return new ResponseEntity<String>("Unmapped request", HttpStatus.OK);
        }

    }

}
