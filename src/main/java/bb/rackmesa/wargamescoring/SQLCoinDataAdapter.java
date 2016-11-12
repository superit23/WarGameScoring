package bb.rackmesa.wargamescoring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Dan on 11/12/2016.
 */
public class SQLCoinDataAdapter implements ICoinDataAdapter {

    static Logger logger = LogManager.getLogger();

    public Coin CreateCoin(String initialUser) {
        Coin newCoin = new Coin();
        newCoin.setInitialUser(initialUser);

        DatabaseFunctions.Insert("INSERT INTO coins(uuid, initialUser) VALUES(?,?);", new Object[]{newCoin.getCoin().toString(), newCoin.getInitialUser()});

        return newCoin;
    }

    public Coin RetrieveCoin(String uuid) {
        try {
            ResultSet rs = DatabaseFunctions.Retrieve("SELECT * FROM coins WHERE uuid = ?;", new Object[]{ uuid });

            if(rs.next()) {
                Coin coin = new Coin(uuid);
                coin.setInitialUser(rs.getString("initialUser"));
                return coin;
            }
            else
            {
                logger.error("RetrieveCoin: Resultset is empty!");
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<Coin> RetrieveCoinsForUser(User user) {
        return RetrieveCoinsForUser(user.getUserName());
    }

    public ArrayList<Coin> RetrieveCoinsForUser(String username) {
        try {
            ResultSet rs = DatabaseFunctions.Retrieve("SELECT * FROM coins WHERE initialUser = ?;", new Object[]{ username });
            ArrayList<Coin> coins = new ArrayList<>();

            while(rs.next()) {
                Coin coin = new Coin(rs.getString("uuid"));
                coin.setInitialUser(rs.getString("initialUser"));
                coins.add(coin);
            }

            return coins;

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void DeleteCoin(UUID id) {
        DatabaseFunctions.Insert("DELETE FROM coins WHERE uuid = ?;", new Object[]{id.toString()});

    }
}
