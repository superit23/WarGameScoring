package bb.rackmesa.wargamescoring;

import java.util.ArrayList;

/**
 * Created by Dan on 9/8/2016.
 */
public class Main {

    public static ArrayList<Coin> coinsToCommit = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        for(int i = 1; i < args.length; i++) {
            if (args[i] == "install") {
                DatabaseFunctions.CreateUsersTable();
                DatabaseFunctions.CreateCoinsTable();
                Configuration.getConfig().userAdapter.CreateUser("admin", "admin", "admin", "admin", 0);

                break;
            }
        }
    }

}
