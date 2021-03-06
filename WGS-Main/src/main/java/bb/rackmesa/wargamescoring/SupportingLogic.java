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

    public static void TransferScore(User sender, User receiver, int score) throws Exception
    {
        if(score > 0 && sender.getScore() > 0) {
            int modScore = score > sender.getScore() ? sender.getScore() : score;

            sender.setScore(sender.getScore() - modScore);
            receiver.setScore(receiver.getScore() + modScore);

            configuration.userAdapter.UpdateUser(sender);
            configuration.userAdapter.UpdateUser(receiver);
        }
        else
        {
            throw new IllegalArgumentException("Score cannot be less than one.");
        }
    }

    public static void DepositCoin(Coin coin)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour == configuration.depositWindow)
        {
            Main.coinsToCommit.add(coin);
        }
        else
        {
            throw new IllegalStateException("The deposit window is not currently open. Window set to " + configuration.getDepositWindow());
        }

    }

    public static void CommitCoins() throws Exception
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
                List<User> users = coins.stream()
                        .filter(coin -> coin.getSubmitter() != null)
                        .map(coin -> configuration.userAdapter.RetrieveUser(coin.getSubmitter()))
                        .filter(user -> user != null).collect(Collectors.toList());

                logger.debug("Submitting users are: " + users.stream().map(user -> user.getUserName() + "\n").reduce((uname1, uname2) -> uname1 + uname2).get());

                Coin coin = coins.get(0);

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
