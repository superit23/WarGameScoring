import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;

import java.util.ArrayList;

/**
 * Created by Dan on 9/8/2016.
 */
public class Main {

    public static ArrayList<Coin> coinsToCommit = new ArrayList<>();

    public static void main(String[] args) {

    }

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
