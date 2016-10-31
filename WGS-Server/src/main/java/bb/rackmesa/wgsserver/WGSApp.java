package bb.rackmesa.wgsserver;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Dan on 10/31/2016.
 */
public class WGSApp extends ResourceConfig {
    public WGSApp()
    {
        register(org.glassfish.jersey.jackson.JacksonFeature.class);
    }
}
