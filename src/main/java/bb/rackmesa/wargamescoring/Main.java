package bb.rackmesa.wargamescoring;

import java.sql.DriverManager;
import java.util.ArrayList;

/**
 * Created by Dan on 9/8/2016.
 */
public class Main {

    public static ArrayList<Coin> coinsToCommit = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        if(args[1] == "install")
        {
            DatabaseFunctions.CreateUsersTable();
            DatabaseFunctions.CreateCoinsTable();
            Configuration.getConfig().userAdapter.CreateUser("admin", "admin", "admin", "admin", 0);
        }
    }

}
