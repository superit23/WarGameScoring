import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Dan on 9/20/2016.
 */
public class DatabaseFunctionsTest {
    @Test
    public void userCRUD() throws Exception {
        Configuration.Init();

        User tUser1 = DatabaseFunctions.CreateUser("tUser", "tPass", "tRole", "team1", 0);
        User tUser2 = DatabaseFunctions.RetrieveUser(tUser1.getUserName());

        Assert.assertEquals(tUser1.getUserName(), tUser2.getUserName());
        Assert.assertEquals(tUser1.getCredentialsSalt().toBase64(), tUser2.getCredentialsSalt().toBase64());
        Assert.assertEquals(tUser1.getPassword(), tUser2.getPassword());
        Assert.assertEquals(tUser1.getRole(), tUser2.getRole());
        Assert.assertEquals(tUser1.getScore(), tUser2.getScore());
        Assert.assertEquals(tUser1.getTeam(), tUser2.getTeam());

        tUser1.setPassword("NEWPASSBOIS");
        DatabaseFunctions.UpdateUserPassword(tUser1);
        tUser2 = DatabaseFunctions.RetrieveUser(tUser1.getUserName());
        Assert.assertEquals(tUser1.getPassword(), tUser2.getPassword());


        tUser1.setScore(10);
        DatabaseFunctions.UpdateUserScore(tUser1);
        tUser2 = DatabaseFunctions.RetrieveUser(tUser1.getUserName());
        Assert.assertEquals(tUser1.getScore(), tUser2.getScore());

        DatabaseFunctions.DeleteUser(tUser1);

        tUser2 = DatabaseFunctions.RetrieveUser(tUser1.getUserName());
        Assert.assertEquals(tUser2, null);

    }

    @Test
    public void coinCRUD() throws Exception {
        Configuration.Init();

        User user = DatabaseFunctions.RetrieveUser("tUser");

        if(user ==  null)
        {
            user = DatabaseFunctions.CreateUser("tUser", "tPass", "tRole", "team1", 30);
        }

        Coin tCoin1 = DatabaseFunctions.CreateCoin(user.getUserName());
        Coin tCoin2 = DatabaseFunctions.RetrieveCoin(tCoin1.getCoin().toString());

        Assert.assertEquals(tCoin1.getInitialUser(), tCoin2.getInitialUser());

        DatabaseFunctions.DeleteCoin(tCoin1.getCoin());

        tCoin2 = DatabaseFunctions.RetrieveCoin(tCoin1.getCoin().toString());
        Assert.assertEquals(tCoin2, null);

        DatabaseFunctions.DeleteUser(user);
    }

    @Test
    public void transferScore() throws Exception {
        Configuration.Init();

        User user = DatabaseFunctions.RetrieveUser("tUser");

        if(user ==  null)
        {
            user = DatabaseFunctions.CreateUser("tUser", "tPass", "tRole", "team1", 30);
        }
        else
        {
            user.setScore(30);
            DatabaseFunctions.UpdateUserScore(user);
        }


        User user2 = DatabaseFunctions.RetrieveUser("tUser1");

        if(user2 ==  null)
        {
            user2 = DatabaseFunctions.CreateUser("tUser1", "tPass", "tRole", "team1", 20);
        }
        else
        {
            user2.setScore(20);
            DatabaseFunctions.UpdateUserScore(user2);
        }


        DatabaseFunctions.TransferScore(user, user2, 20);
        user = DatabaseFunctions.RetrieveUser("tUser");
        user2 = DatabaseFunctions.RetrieveUser("tUser1");

        Assert.assertTrue(user.getScore() == 10);
        Assert.assertTrue(user2.getScore() == 40);

        DatabaseFunctions.TransferScore(user, user2, 20);
        user = DatabaseFunctions.RetrieveUser("tUser");
        user2 = DatabaseFunctions.RetrieveUser("tUser1");

        Assert.assertTrue(user.getScore() == 0);
        Assert.assertTrue(user2.getScore() == 50);

        DatabaseFunctions.DeleteUser(user);
        DatabaseFunctions.DeleteUser(user2);

    }

    @Test
    public void retrieveCoinsByUser() throws Exception
    {
        Configuration.Init();

        User user = DatabaseFunctions.RetrieveUser("tUser");

        if(user ==  null)
        {
            user = DatabaseFunctions.CreateUser("tUser", "tPass", "tRole", "team1", 30);
        }

        Coin coin1 = DatabaseFunctions.CreateCoin(user.getUserName());
        Coin coin2 = DatabaseFunctions.CreateCoin(user.getUserName());

        ArrayList<Coin> coins = DatabaseFunctions.RetrieveCoinsForUser(user);

        Assert.assertTrue(coins.contains(coin1));
        Assert.assertTrue(coins.contains(coin2));

        DatabaseFunctions.DeleteCoin(coin1.getCoin());
        DatabaseFunctions.DeleteCoin(coin2.getCoin());
    }

    @Test
    public void depositCoin() throws Exception
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        Configuration.windowStart = (hour + 1) % 24;
        Configuration.windowEnd = (hour + 2) % 24;

        Coin tCoin = new Coin();
        assertFalse(DatabaseFunctions.DepositCoin(tCoin));

        Configuration.windowStart = (hour - 1) % 24;
        Configuration.windowEnd = (hour + 1) % 24;

        assertTrue(DatabaseFunctions.DepositCoin(tCoin));


    }

    @Test
    public void commitCoins() throws Exception
    {
        Configuration.Init();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        Configuration.windowStart = (hour - 1) % 24;
        Configuration.windowEnd = (hour + 1) % 25;

        ArrayList<User> users = new ArrayList<>();

        for(int i = 0; i < 4; i++)
        {
            String role =  i % 2 == 0 ? Constants.USER_ROLE : Constants.TEAM_ROLE;
            String team = i < 2 ? "team1" : "team2";

            users.add(DatabaseFunctions.CreateUser("tUser" + i, "tPass", role, team, 0));
        }


        ArrayList<Coin> coins = new ArrayList<>();
        int usrCount = users.toArray().length;

        for(int i = 0; i < usrCount; i++) {
            coins.add(DatabaseFunctions.CreateCoin(users.get(i).getUserName()));
        }


        int coinsCount = coins.toArray().length;

        for(int i = 0; i < coinsCount; i++) {
            Coin coin = coins.get(i);

            for(int j = i; j < usrCount; j++) {
                Coin nCoin = new Coin(coin.getCoin().toString());
                nCoin.setInitialUser(coin.getInitialUser());
                nCoin.setSubmitter(users.get(j).getUserName());

                DatabaseFunctions.DepositCoin(nCoin);
            }

        }

        DatabaseFunctions.CommitCoins();

        for(int i = 0; i < usrCount; i++) {
            User cUser = users.get(i);
            users.remove(i);
            cUser = DatabaseFunctions.RetrieveUser(cUser.getUserName());

            users.add(i, cUser);
        }

        assertEquals(users.get(0).getScore(), 0);
        assertEquals(users.get(1).getScore(), 0);
        assertEquals(users.get(2).getScore(), 3);
        assertEquals(users.get(3).getScore(), 1);

        for(int i = 0; i < usrCount; i++) {
            DatabaseFunctions.DeleteUser(users.get(i));
        }


    }

}