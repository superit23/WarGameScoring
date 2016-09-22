import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Dan on 9/20/2016.
 */
public class DatabaseFunctionsTest {
    @Test
    public void userCRUD() throws Exception {
        Main.Init();

        User tUser1 = DatabaseFunctions.CreateUser("tUser", "tPass", "tRole", 0);
        User tUser2 = DatabaseFunctions.RetrieveUser(tUser1.getUserName());

        Assert.assertEquals(tUser1.getUserName(), tUser2.getUserName());
        Assert.assertEquals(tUser1.getCredentialsSalt().toBase64(), tUser2.getCredentialsSalt().toBase64());
        Assert.assertEquals(tUser1.getPassword(), tUser2.getPassword());
        Assert.assertEquals(tUser1.getRole(), tUser2.getRole());
        Assert.assertEquals(tUser1.getScore(), tUser2.getScore());

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
        Main.Init();

        Coin tCoin1 = DatabaseFunctions.CreateCoin("tUser");
        Coin tCoin2 = DatabaseFunctions.RetrieveCoin(tCoin1.getCoin().toString());

        Assert.assertEquals(tCoin1.getInitialUser(), tCoin2.getInitialUser());

        DatabaseFunctions.DeleteCoin(tCoin1.getCoin());

        tCoin2 = DatabaseFunctions.RetrieveCoin(tCoin1.getCoin().toString());
        Assert.assertEquals(tCoin2, null);
    }

    @Test
    public void transferScore() throws Exception {
        Main.Init();

        User user = DatabaseFunctions.RetrieveUser("tUser");

        if(user ==  null)
        {
            user = DatabaseFunctions.CreateUser("tUser", "tPass", "tRole", 30);
        }
        else
        {
            user.setScore(30);
            DatabaseFunctions.UpdateUserScore(user);
        }


        User user2 = DatabaseFunctions.RetrieveUser("tUser1");

        if(user2 ==  null)
        {
            user2 = DatabaseFunctions.CreateUser("tUser1", "tPass", "tRole", 20);
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
        Main.Init();

        User user = DatabaseFunctions.RetrieveUser("tUser");

        if(user ==  null)
        {
            user = DatabaseFunctions.CreateUser("tUser", "tPass", "tRole", 30);
        }

        Coin coin1 = DatabaseFunctions.CreateCoin(user.getUserName());
        Coin coin2 = DatabaseFunctions.CreateCoin(user.getUserName());

        ArrayList<Coin> coins = DatabaseFunctions.RetrieveCoinsForUser(user);

        Assert.assertTrue(coins.contains(coin1));
        Assert.assertTrue(coins.contains(coin2));

        DatabaseFunctions.DeleteCoin(coin1.getCoin());
        DatabaseFunctions.DeleteCoin(coin2.getCoin());
    }


}