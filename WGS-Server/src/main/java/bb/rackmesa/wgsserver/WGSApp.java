package bb.rackmesa.wgsserver;

import bb.rackmesa.wargamescoring.Configuration;
import bb.rackmesa.wargamescoring.SupportingLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dan on 10/31/2016.
 */
public class WGSApp extends ResourceConfig {

    private static Logger logger = LogManager.getLogger();

    public WGSApp() throws Exception
    {
        register(org.glassfish.jersey.jackson.JacksonFeature.class);

        Configuration.RegisterDrivers();

        // http://stackoverflow.com/questions/20387881/how-to-run-certain-task-every-day-at-a-particular-time-using-scheduledexecutorse
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of("America/Chicago");

        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNext;

        zonedNext = zonedNow.withHour(Configuration.getConfig().depositWindow + 1 % 24).withMinute(0).withSecond(0);

        if(zonedNow.compareTo(zonedNext) > 0)
            zonedNext = zonedNext.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNext);
        long initalDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(()-> {
            try {
                SupportingLogic.CommitCoins();
            }
            catch (Exception ex)
            {
                logger.error(ex.getMessage());
            }

        }, initalDelay, 24*60*60, TimeUnit.SECONDS);
    }


}
