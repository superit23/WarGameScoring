package bb.rackmesa.wgsserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Dan on 11/8/2016.
 */
public class RESTHelper {

    static Logger logger = LogManager.getLogger();

    public static String CleanString(String string)
    {
        try
        {
            return new String(string.getBytes("UTF-8"), "UTF-8");
        }
        catch (UnsupportedEncodingException ex)
        {
            logger.error(ex.getMessage());
        }

        return string;
    }

    public static boolean CompareUncleanStrings(String string1, String string2)
    {
        return CleanString(string1).equals(CleanString(string2));
    }

    public static void checkAuth(Subject subject)
    {
        if(!subject.isAuthenticated()) {
            throw new AuthenticationException("Subject is not authenticated.");
        }
    }

}
