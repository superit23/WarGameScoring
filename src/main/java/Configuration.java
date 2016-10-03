import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;


/**
 * Created by Dan on 9/8/2016.
 */
public class Configuration {

    public static String WGS_REALM = "wgs";
    public static int window = 19;
    public static String commitTime = "21:00:05";

    public static IPriorityTable priorityTable;

    public static String connectionString = "";
    public static int pbkdf2Iterations;
    public static int pbkdf2NumBytes;
    public static int saltLength;

    public static void Init()
    {
        Ini ini = new Ini();
        ini.loadFromPath("C:\\wgs.ini");

        Factory<SecurityManager> factory = new IniSecurityManagerFactory(ini);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);


        Configuration.pbkdf2Iterations = 2048;
        Configuration.pbkdf2NumBytes = 32;
        Configuration.saltLength = 4;
        Configuration.connectionString = ((SimpleDBRealm)((DefaultSecurityManager)SecurityUtils.getSecurityManager()).getRealms().iterator().next()).getConnectionString();

        Configuration.priorityTable = new WorstPriorityTable();
    }
}
