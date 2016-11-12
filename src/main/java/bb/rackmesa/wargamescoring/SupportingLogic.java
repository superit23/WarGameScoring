package bb.rackmesa.wargamescoring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dan on 11/12/2016.
 */
public class SupportingLogic {

    static Logger logger = LogManager.getLogger();
    static Configuration configuration = Configuration.getConfig();

    public static void TransferScore(User sender, User receiver, int score)
    {
        if(score > 0 && sender.getScore() > 0) {
            int modScore = score > sender.getScore() ? sender.getScore() : score;

            sender.setScore(sender.getScore() - modScore);
            receiver.setScore(receiver.getScore() + modScore);

            configuration.userAdapter.UpdateUser(sender);
            configuration.userAdapter.UpdateUser(receiver);
        }
    }

    public static boolean DepositCoin(Coin coin)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour == configuration.depositWindow)
        {
            Main.coinsToCommit.add(coin);
            return true;
        }

        return false;
    }

    public static void CommitCoins()
    {
        logger.info("Starting commit.");
        HashMap<String, ArrayList<Coin>> coinsByUUID = new HashMap<>();
        for (Coin coin:
                Main.coinsToCommit) {

            if(!coinsByUUID.containsKey(coin.getCoin().toString()))
            {
                coinsByUUID.put(coin.getCoin().toString(), new ArrayList<>());
            }

            coinsByUUID.get(coin.getCoin().toString()).add(coin);
        }

        for (String cUUID:
                coinsByUUID.keySet()) {

            ArrayList<Coin> coins = coinsByUUID.get(cUUID);

            if(configuration.coinAdapter.RetrieveCoin(cUUID) != null) {
                List<User> users = coins.stream().map(coin -> configuration.userAdapter.RetrieveUser(coin.getSubmitter())).collect(Collectors.toList());

                logger.debug("Submitting users are: " + users.stream().map(user -> user.getUserName() + "\n").reduce((uname1, uname2) -> uname1 + uname2).get());

                Coin coin = coins.get(0);
                configuration.userAdapter.RetrieveUser(coin.getInitialUser());

                logger.info("Running priority table on user collection.");
                User user = configuration.priorityTable.prioritize(coin, users);
                user.setScore(user.getScore() + 1);

                logger.info("Updating user " + user.getUserName());
                configuration.userAdapter.UpdateUser(user);
            }
            else
            {
                logger.warn("Coin " + cUUID + " submitted for " + coins.get(0).getSubmitter() + " not in database!");
            }
        }


    }
}
