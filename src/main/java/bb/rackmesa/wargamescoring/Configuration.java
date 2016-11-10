package bb.rackmesa.wargamescoring;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;

import java.sql.DriverManager;


/**
 * Created by Dan on 9/8/2016.
 */
public class Configuration {

    public static final String WGS_REALM = "wgs";
    public static int depositWindow = 19;

    public static IPriorityTable priorityTable;

    public static int pbkdf2Iterations = 2048;
    public static int pbkdf2NumBytes = 32;
    public static int saltLength = 4;


    public static void Init()
    {
        Ini ini = new Ini();
        ini.loadFromPath("C:\\wgs.ini");

        Factory<SecurityManager> factory = new IniSecurityManagerFactory(ini);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

    }

    public static void EasyConf()
    {
        if(priorityTable == null) {
            priorityTable = new WorstPriorityTable();
        }

        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }
        catch (Exception ex)
        {

        }
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
        return ((SimpleDBRealm)((DefaultSecurityManager)SecurityUtils.getSecurityManager()).getRealms().iterator().next()).getConnectionString();
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
