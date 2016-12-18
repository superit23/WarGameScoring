package bb.rackmesa.wargamescoring;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by Dan on 9/8/2016.
 */
public class Configuration {


    public int depositWindow = 19;

    public IPriorityTable priorityTable = new WorstPriorityTable();
    public IUserDataAdapter userAdapter = new SQLUserDataAdapter();
    public ICoinDataAdapter coinAdapter = new SQLCoinDataAdapter();

    public int pbkdf2Iterations = 2048;
    public int pbkdf2NumBytes = 32;
    public int saltLength = 4;

    public String serverURL = "http://localhost:8080/";

    public Driver jdbcDriver;

    public static ConfigurableSecurityManager securityManager;

    public Configuration()
    {
//        try{
//            jdbcDriver = new com.mysql.cj.jdbc.Driver();
//        }
//        catch (SQLException ex)
//        {
//            jdbcDriver = null;
//        }
    }

    public static void Init()
    {
        Ini ini = new Ini();
        ini.loadFromPath("C:\\wgs.ini");

        Factory<SecurityManager> factory = new IniSecurityManagerFactory(ini);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

    }

    public static void RegisterDrivers() throws SQLException
    {
        Driver driver = getConfig().jdbcDriver;

        if(driver != null) {
            DriverManager.registerDriver(driver);
        }
    }

    public static Configuration getConfig()
    {
        return securityManager.getConfiguration();
    }

    public int getDepositWindow() {
        return depositWindow;
    }

    public int getPbkdf2Iterations() {
        return pbkdf2Iterations;
    }

    public int getPbkdf2NumBytes() {
        return pbkdf2NumBytes;
    }

    public int getSaltLength() {
        return saltLength;
    }

    public IPriorityTable getPriorityTable() {
        return priorityTable;
    }

    public static String getConnectionString() {
        return ((SimpleDBRealm)((DefaultSecurityManager)SecurityUtils.getSecurityManager()).getRealms().iterator().next()).getConnectionString();
    }

    public void setDepositWindow(int depositWindow) {
        this.depositWindow = depositWindow;
    }

    public void setPbkdf2Iterations(int pbkdf2Iterations) {
        this.pbkdf2Iterations = pbkdf2Iterations;
    }

    public void setPbkdf2NumBytes(int pbkdf2NumBytes) {
        this.pbkdf2NumBytes = pbkdf2NumBytes;
    }

    public void setPriorityTable(IPriorityTable priorityTable) {
        this.priorityTable = priorityTable;
    }

    public void setSaltLength(int saltLength) {
        this.saltLength = saltLength;
    }

    public ICoinDataAdapter getCoinAdapter() {
        return coinAdapter;
    }

    public void setCoinAdapter(ICoinDataAdapter coinAdapter) {
        this.coinAdapter = coinAdapter;
    }

    public IUserDataAdapter getUserAdapter() {
        return userAdapter;
    }

    public void setUserAdapter(IUserDataAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public void setJdbcDriver(Driver jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }
}
