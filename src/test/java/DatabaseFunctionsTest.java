import bb.rackmesa.wargamescoring.*;
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
        Configuration.RegisterDrivers();

        Configuration configuration = Configuration.getConfig();

        User tUser1 = configuration.userAdapter.CreateUser("tUser10", "tPass", "tRole", "team1", 0);
        User tUser2 = configuration.userAdapter.RetrieveUser(tUser1.getUserName());

        Assert.assertEquals(tUser1.getUserName(), tUser2.getUserName());
        Assert.assertEquals(tUser1.getCredentialsSalt().toBase64(), tUser2.getCredentialsSalt().toBase64());
        Assert.assertEquals(tUser1.getPassword(), tUser2.getPassword());
        Assert.assertEquals(tUser1.getRole(), tUser2.getRole());
        Assert.assertEquals(tUser1.getScore(), tUser2.getScore());
        Assert.assertEquals(tUser1.getTeam(), tUser2.getTeam());

        tUser1.setPassword("NEWPASSBOIS");
        configuration.userAdapter.UpdateUser(tUser1);
        tUser2 = configuration.userAdapter.RetrieveUser(tUser1.getUserName());
        Assert.assertEquals(tUser1.getPassword(), tUser2.getPassword());


        tUser1.setScore(10);
        configuration.userAdapter.UpdateUser(tUser1);
        tUser2 = configuration.userAdapter.RetrieveUser(tUser1.getUserName());
        Assert.assertEquals(tUser1.getScore(), tUser2.getScore());

        configuration.userAdapter.DeleteUser(tUser1);

        tUser2 = configuration.userAdapter.RetrieveUser(tUser1.getUserName());
        Assert.assertEquals(tUser2, null);

    }

    @Test
    public void coinCRUD() throws Exception {
        Configuration.Init();
        Configuration.RegisterDrivers();

        Configuration configuration = Configuration.getConfig();

        User user = configuration.userAdapter.RetrieveUser("tUser");

        if(user ==  null)
        {
            user = configuration.userAdapter.CreateUser("tUser", "tPass", "tRole", "team1", 30);
        }

        Coin tCoin1 = configuration.coinAdapter.CreateCoin(user.getUserName());
        Coin tCoin2 = configuration.coinAdapter.RetrieveCoin(tCoin1.getCoin().toString());

        Assert.assertEquals(tCoin1.getInitialUser(), tCoin2.getInitialUser());

        configuration.coinAdapter.DeleteCoin(tCoin1.getCoin());

        tCoin2 = configuration.coinAdapter.RetrieveCoin(tCoin1.getCoin().toString());
        Assert.assertEquals(tCoin2, null);

        configuration.userAdapter.DeleteUser(user);
    }

    @Test
    public void transferScore() throws Exception {
        Configuration.Init();
        Configuration configuration = Configuration.getConfig();

        User user = configuration.userAdapter.RetrieveUser("tUser");

        if(user ==  null)
        {
            user = configuration.userAdapter.CreateUser("tUser", "tPass", "tRole", "team1", 30);
        }
        else
        {
            user.setScore(30);
            configuration.userAdapter.UpdateUser(user);
        }


        User user2 = configuration.userAdapter.RetrieveUser("tUser1");

        if(user2 ==  null)
        {
            user2 = configuration.userAdapter.CreateUser("tUser1", "tPass", "tRole", "team1", 20);
        }
        else
        {
            user2.setScore(20);
            configuration.userAdapter.UpdateUser(user2);
        }


        SupportingLogic.TransferScore(user, user2, 20);
        user = configuration.userAdapter.RetrieveUser("tUser");
        user2 = configuration.userAdapter.RetrieveUser("tUser1");

        Assert.assertTrue(user.getScore() == 10);
        Assert.assertTrue(user2.getScore() == 40);

        SupportingLogic.TransferScore(user, user2, 20);
        user = configuration.userAdapter.RetrieveUser("tUser");
        user2 = configuration.userAdapter.RetrieveUser("tUser1");

        Assert.assertTrue(user.getScore() == 0);
        Assert.assertTrue(user2.getScore() == 50);

        configuration.userAdapter.DeleteUser(user);
        configuration.userAdapter.DeleteUser(user2);

    }

    @Test
    public void retrieveCoinsByUser() throws Exception
    {
        Configuration.Init();
        Configuration.RegisterDrivers();

        Configuration configuration = Configuration.getConfig();

        User user = configuration.userAdapter.RetrieveUser("tUser");

        if(user ==  null)
        {
            user = configuration.userAdapter.CreateUser("tUser", "tPass", "tRole", "team1", 30);
        }

        Coin coin1 = configuration.coinAdapter.CreateCoin(user.getUserName());
        Coin coin2 = configuration.coinAdapter.CreateCoin(user.getUserName());

        ArrayList<Coin> coins = configuration.coinAdapter.RetrieveCoinsForUser(user);

        Assert.assertTrue(coins.contains(coin1));
        Assert.assertTrue(coins.contains(coin2));

        configuration.userAdapter.DeleteUser(user);
    }

    @Test
    public void depositCoin() throws Exception
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Configuration configuration = Configuration.getConfig();

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        configuration.depositWindow = (hour + 1) % 24;

        Coin tCoin = new Coin();
        assertFalse(SupportingLogic.DepositCoin(tCoin));

        configuration.depositWindow = (hour);

        assertTrue(SupportingLogic.DepositCoin(tCoin));


    }

    @Test
    public void commitCoins() throws Exception
    {
        Configuration.Init();
        Configuration.RegisterDrivers();

        Configuration configuration = Configuration.getConfig();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        configuration.depositWindow = (hour);

        ArrayList<User> users = new ArrayList<>();

        for(int i = 0; i < 4; i++)
        {
            String role =  i % 2 == 0 ? Constants.USER_ROLE : Constants.TEAM_ROLE;
            String team = i < 2 ? "team1" : "team2";

            users.add(configuration.userAdapter.CreateUser("tUser" + i, "tPass", role, team, 0));
        }


        ArrayList<Coin> coins = new ArrayList<>();
        int usrCount = users.toArray().length;

        for(int i = 0; i < usrCount; i++) {
            coins.add(configuration.coinAdapter.CreateCoin(users.get(i).getUserName()));
        }


        int coinsCount = coins.toArray().length;

        for(int i = 0; i < coinsCount; i++) {
            Coin coin = coins.get(i);

            for(int j = i; j < usrCount; j++) {
                Coin nCoin = new Coin(coin.getCoin().toString());
                nCoin.setInitialUser(coin.getInitialUser());
                nCoin.setSubmitter(users.get(j).getUserName());

                SupportingLogic.DepositCoin(nCoin);
            }

        }

        SupportingLogic.CommitCoins();

        for(int i = 0; i < usrCount; i++) {
            User cUser = users.get(i);
            users.remove(i);
            cUser = configuration.userAdapter.RetrieveUser(cUser.getUserName());

            users.add(i, cUser);
        }

        assertEquals(users.get(0).getScore(), 0);
        assertEquals(users.get(1).getScore(), 0);
        assertEquals(users.get(2).getScore(), 3);
        assertEquals(users.get(3).getScore(), 1);

        for(int i = 0; i < usrCount; i++) {
            configuration.userAdapter.DeleteUser(users.get(i));
        }


    }

    @Test
    public void makeUser() throws Exception
    {
        Configuration.Init();
        Configuration.RegisterDrivers();

        Configuration configuration = Configuration.getConfig();

        User tUser1 = configuration.userAdapter.CreateUser("tUser", "tPass", "tRole", "team1", 0);
    }

    @Test
    public void makeAdmin() throws Exception
    {
        Configuration.Init();
        Configuration.RegisterDrivers();

        Configuration configuration = Configuration.getConfig();

        User tUser1 = configuration.userAdapter.CreateUser("admin", "admin", "admin", "team1", 0);
    }

    @Test
    public void fixAdmin() throws Exception {
        Configuration.Init();
        Configuration.RegisterDrivers();

        Configuration configuration = Configuration.getConfig();

        User user = new User("admin");
        user.setPassword("admin");
        user.setRole("admin");

        configuration.userAdapter.UpdateUser(user);
    }
}