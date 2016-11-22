import bb.rackmesa.wargamescoring.Coin;
import bb.rackmesa.wargamescoring.ICoinDataAdapter;
import bb.rackmesa.wargamescoring.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Alex on 11/15/2016.
 */
public class CoinDataAdapter implements ICoinDataAdapter {

    private String sessionCookie;
    private String url = "http://127.0.0.1/coin";
    private HttpClient client = HttpClientBuilder.create().build();

    //TODO test with server
    @Override
    public Coin CreateCoin(String initialUser) {
        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("cookie", sessionCookie);
            post.setHeader("username", initialUser);
            HttpResponse response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //TODO  why does this method exist
    @Override
    public Coin RetrieveCoin(String uuid) {
        return null;
    }

    //TODO test with server "Why is there a User argument"
    @Override
    public ArrayList<Coin> RetrieveCoinsForUser(User user) {
        try {
            HttpGet get = new HttpGet(url);
            get.setHeader("cookie", sessionCookie);
            HttpResponse response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO test with server "Why is there a username argument Better yet why are there 2 of them."
    @Override
    public ArrayList<Coin> RetrieveCoinsForUser(String username) {
        try {
            HttpGet get = new HttpGet(url);
            get.setHeader("cookie", sessionCookie);
            HttpResponse response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO test with server
    @Override
    public void DeleteCoin(UUID id) {


    }
}
