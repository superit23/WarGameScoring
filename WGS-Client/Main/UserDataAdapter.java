import bb.rackmesa.wargamescoring.IUserDataAdapter;
import bb.rackmesa.wargamescoring.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by Alex on 11/15/2016.
 */
public class UserDataAdapter implements IUserDataAdapter {


    private String sessionCookie;
    private String serverURL = "http://127.0.0.1:8080/WGS-Server/";
    private String auth = "auth/";
    private String userPath = "user/";
    private String Cookie = "Cookie";
    private HttpClient client = HttpClientBuilder.create().build();

    public UserDataAdapter(){

    }

    public String getSessionCookie(){
        return sessionCookie;
    }

    public void setSessionCookie(String sessionCookie){
        this.sessionCookie = sessionCookie;
    }

    public boolean login(String username, String password){
        boolean login =false;
        try {
            CookieHandler.setDefault(new CookieManager());
            HttpPost httpPost = new HttpPost( serverURL + auth + "login");
            httpPost.setHeader("username", username);
            httpPost.setHeader("password", password);
            HttpResponse response = client.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == 200){
                sessionCookie = response.getFirstHeader("Set-Cookie").toString().substring(12);
                login = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }

    public void logout(){
        try {
            HttpPost httpPost = new HttpPost(serverURL + auth + "logout");
            client.execute(httpPost);
            sessionCookie = "";
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO search user to see if they exist.
    @Override
    public User CreateUser(String username, String password, String role, String team, int score){
        try {
            HttpPost post = new HttpPost(serverURL + userPath);
            post.setHeader(Cookie, sessionCookie);
            post.setHeader("username", username);
            post.setHeader("password", password);
            post.setHeader("role", role);
            post.setHeader("team", team);
            post.setHeader("score", Integer.toString(score));
            HttpResponse response = client.execute(post);

            if((response.getStatusLine().getStatusCode() == 200)){
                System.out.println("The user was created.");
            }
            else{
                System.out.println("The user was not created.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO
    @Override
    public User RetrieveUser(String username) {
        User user = null;
        try {
            HttpGet get = new HttpGet(serverURL + userPath);
            get.setHeader(Cookie, sessionCookie);
            get.setHeader("username", username);
            HttpResponse response = client.execute(get);

            if(response.getStatusLine().getStatusCode() == 200){
                String json = IOUtils.toString(response.getEntity().getContent());

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(json);
                JSONObject jsonObject = (JSONObject)obj;
                user.setUserName(jsonObject.get("userName").toString());
                user.setScore(Integer.parseInt(jsonObject.get("score").toString()));
                user.setTeam(jsonObject.get("team").toString());
                user.setRole(jsonObject.get("role").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    //TODO
    @Override
    public void UpdateUser(User user) {
        try {
            HttpPut put = new HttpPut(serverURL + userPath);
            put.setHeader(Cookie, sessionCookie);
            put.setHeader("username", user.getUserName());
            put.setHeader("password", user.getPassword());
            put.setHeader("role", user.getRole());
            put.setHeader("team", user.getTeam());
            put.setHeader("score", Integer.toString(user.getScore()));
            HttpResponse response = client.execute(put);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void DeleteUser(User user) {


    }

    //TODO always returns sucess.
    @Override
    public void DeleteUser(String username) {
        try {
            HttpDelete delete = new HttpDelete(serverURL + userPath);
            delete.setHeader(Cookie, sessionCookie);
            delete.setHeader("username", username);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode() == 204){
                System.out.println("The account " + username + " was deleted.");
            }
            else {
                System.out.println("The account " + username + " either does not exist or you do not have permission to\n" +
                        "delete it. Please try again.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
