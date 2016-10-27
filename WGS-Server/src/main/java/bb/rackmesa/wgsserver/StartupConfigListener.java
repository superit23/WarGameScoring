package bb.rackmesa.wgsserver;

import javax.servlet.ServletContextEvent;
import bb.rackmesa.wargamescoring.Configuration;

/**
 * Created by Dan on 10/25/2016.
 */
public class StartupConfigListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent context) {
        Configuration.EasyConf();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
