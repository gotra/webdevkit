package eu.olaf.afis;

import eu.olaf.afis.model.Resource;
import eu.olaf.afis.model.StaticResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



/**
 * Created by rguru on 10/08/2017.
 * */

@Configuration
public class StaticResourceHandler extends WebMvcConfigurerAdapter {

    @Autowired
    private StaticResources staticResources;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        for(Resource resource: staticResources.getResourceList()) {

            registry
                    .addResourceHandler(resource.getUri())
                    .addResourceLocations(resource.getLocation());

        }

    }


}
