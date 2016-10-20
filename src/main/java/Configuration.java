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
    public static int depositWindow = 19;

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

        Configuration.connectionString = ((SimpleDBRealm)((DefaultSecurityManager)SecurityUtils.getSecurityManager()).getRealms().iterator().next()).getConnectionString();
    }

    public static void EasyConf()
    {
        Configuration.pbkdf2Iterations = 2048;
        Configuration.pbkdf2NumBytes = 32;
        Configuration.saltLength = 4;

        Configuration.priorityTable = new WorstPriorityTable();
    }

    public static int getDepositWindow() {
        return depositWindow;
    }

    public static int getPbkdf2Iterations() {
        return pbkdf2Iterations;
    }

    public static int getPbkdf2NumBytes() {
        return pbkdf2NumBytes;
    }

    public static int getSaltLength() {
        return saltLength;
    }

    public static IPriorityTable getPriorityTable() {
        return priorityTable;
    }

    public static String getConnectionString() {
        return connectionString;
    }

    public static void setConnectionString(String connectionString) {
        Configuration.connectionString = connectionString;
    }

    public static void setDepositWindow(int depositWindow) {
        Configuration.depositWindow = depositWindow;
    }

    public static void setPbkdf2Iterations(int pbkdf2Iterations) {
        Configuration.pbkdf2Iterations = pbkdf2Iterations;
    }

    public static void setPbkdf2NumBytes(int pbkdf2NumBytes) {
        Configuration.pbkdf2NumBytes = pbkdf2NumBytes;
    }

    public static void setPriorityTable(IPriorityTable priorityTable) {
        Configuration.priorityTable = priorityTable;
    }

    public static void setSaltLength(int saltLength) {
        Configuration.saltLength = saltLength;
    }
}
