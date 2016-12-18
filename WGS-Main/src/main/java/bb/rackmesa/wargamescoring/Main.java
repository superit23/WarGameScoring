package bb.rackmesa.wargamescoring;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dan on 9/8/2016.
 */
public class Main {

    public static ArrayList<Coin> coinsToCommit = new ArrayList<>();


    public static void main(String[] args) throws Exception {

        args = new String[]{"java", "WarGameScoring", "install"};

        Configuration.Init();

        for(int i = 1; i < args.length; i++) {
            if (args[i] == "install") {

                try {
                    DatabaseFunctions.CreateUsersTable();
                }
                catch (SQLException ex)
                {
                    if(!ex.getMessage().contains("No rows generated."))
                        throw ex;
                }

                try {
                    DatabaseFunctions.CreateCoinsTable();
                }
                catch (SQLException ex)
                {
                    if(!ex.getMessage().contains("No rows generated."))
                        throw ex;
                }

                Configuration.getConfig().userAdapter.CreateUser("admin", "admin", "admin", "admin", 0);

                break;
            }
        }
    }

}
