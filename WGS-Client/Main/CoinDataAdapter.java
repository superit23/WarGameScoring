import bb.rackmesa.wargamescoring.Coin;
import bb.rackmesa.wargamescoring.ICoinDataAdapter;
import bb.rackmesa.wargamescoring.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.json.simple.JSONArray;
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

    @Override
    public ArrayList<Coin> RetrieveCoinsForUser(String username) {
        try {
            HttpGet get = new HttpGet(serverURL + "coin");
            get.setHeader(Cookie, sessionCookie);
            HttpResponse response = client.execute(get);
            String json = IOUtils.toString(response.getEntity().getContent());
            ArrayList<Coin> coins = new ArrayList<Coin>();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONArray jsonArray = (JSONArray)obj;

            for(int i=0; i<jsonArray.size(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                coins.add(new Coin(UUID.fromString(jsonObject.get("coin").toString()), jsonObject.get("initialUser").toString()));
            }

            return coins;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO test with server
    public void depositeCoin(String uuid, String username){
        try{
            HttpPost post = new HttpPost(serverURL + "deposit");
            post.setHeader(Cookie, sessionCookie);
            post.setHeader("uuid", uuid);
            post.setHeader("username", username);
            HttpResponse response = client.execute(post);

            if(response.getStatusLine().getStatusCode() != 204)
                System.out.println("Failed to deposit coin" + uuid);
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    //TODO test
    public void TransferScore(String username, int score){
        try{
            HttpPut put = new HttpPut(serverURL + "transfer");
            put.setHeader(Cookie, sessionCookie);
            put.setHeader("username", username);
            put.setHeader("score", Integer.toString(score));
            HttpResponse response = client.execute(put);
            if(response.getStatusLine().getStatusCode() == 204){
                System.out.println("You have transferred " + score + " to " + username);
            }
            else{
                System.out.println("Your attempted transfer has failed. Please try again.");
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //TODO test with server
    @Override
    public void DeleteCoin(UUID id) {


    }
}
