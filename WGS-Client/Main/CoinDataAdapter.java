import bb.rackmesa.wargamescoring.Coin;
import bb.rackmesa.wargamescoring.ICoinDataAdapter;
import bb.rackmesa.wargamescoring.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Alex on 11/15/2016.
 */
public class CoinDataAdapter implements ICoinDataAdapter {

    private String username = "username";
    private String Cookie = "Cookie";
    private String sessionCookie;
    private String serverURL = "http://localhost:8080/WGS-Server/";
    private HttpClient client = HttpClientBuilder.create().build();

    public CoinDataAdapter(){

    }

    public void setSessionCookie(String sessionCookie){
        this.sessionCookie = sessionCookie;
    }

    //TODO test with server
    @Override
    public Coin CreateCoin(String initialUser) {
        Coin coin = new Coin();
        try {
            HttpPost post = new HttpPost(serverURL + "coin");
            post.setHeader(Cookie, sessionCookie);
            post.setHeader(username, initialUser);
            HttpResponse response = client.execute(post);

            if(response.getStatusLine().getStatusCode() == 200){
                String json = IOUtils.toString(response.getEntity().getContent());
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(json);
                JSONObject jsonObject = (JSONObject)obj;
                coin.setInitialUser(initialUser);
            }
            else {
                coin = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return coin;
    }

    @Override
    public Coin RetrieveCoin(String uuid) {
        return null;
    }

    //TODO test with server "Why is there a User argument"
    @Override
    public ArrayList<Coin> RetrieveCoinsForUser(User user) {
        try {
            HttpGet get = new HttpGet(serverURL + "coin");
            get.setHeader(Cookie, sessionCookie);
            HttpResponse response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO test with Server
    @Override
    public ArrayList<Coin> RetrieveCoinsForUser(String username) {
        try {
            HttpGet get = new HttpGet(serverURL + "coin");
            get.setHeader(Cookie, sessionCookie);
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
