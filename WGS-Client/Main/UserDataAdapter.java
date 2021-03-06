import bb.rackmesa.wargamescoring.Configuration;
import bb.rackmesa.wargamescoring.IUserDataAdapter;
import bb.rackmesa.wargamescoring.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

/**
 * Created by Alex on 11/15/2016.
 */
public class UserDataAdapter implements IUserDataAdapter {


    private String sessionCookie;
    //private String serverURL = "http://127.0.0.1:8080/";
    private String serverURL = Configuration.getConfig().getServerURL();
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

    public String login(String username, String password){
        String sessionCookie = null;
        try {
            HttpPost httpPost = new HttpPost( serverURL + auth + "login");
            httpPost.setHeader("username", username);
            httpPost.setHeader("password", password);
            HttpResponse response = client.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == 200){
                sessionCookie = response.getFirstHeader("Set-Cookie").toString().substring(12);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionCookie;
    }

    public void logout(){
        try {
            HttpPost httpPost = new HttpPost(serverURL + auth + "logout");
            httpPost.setHeader(Cookie, sessionCookie);
            HttpResponse response = client.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 204){
                System.out.println("You have logged out");
                sessionCookie = "";
            }
            else
                System.out.println("You are not logged in.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    @Override
    public User RetrieveUser(String username) {
        User user = new User();
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
                if(jsonObject.get("team") == null)
                    user.setTeam("null");
                else
                    user.setTeam(jsonObject.get("team").toString());
                user.setRole(jsonObject.get("role").toString());
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void UpdateUser(User user) {
        try {
            HttpPut put = new HttpPut(serverURL + userPath);
            put.setHeader(Cookie, sessionCookie);
            put.setHeader("username", user.getUserName());

            if(user.getPassword() != null && user.getPassword() != "")
            {
                put.setHeader("credentials", user.getPassword());
                put.setHeader("salt", user.getCredentialsSalt().toBase64());
            }

            put.setHeader("role", user.getRole());
            put.setHeader("team", user.getTeam());
            put.setHeader("score", Integer.toString(user.getScore()));
            HttpResponse response = client.execute(put);

            //System.out.println(IOUtils.toString(response.getEntity().getContent()));
            if(response.getStatusLine().getStatusCode() == 204)
                System.out.println("User data updated.");
            else
                System.out.println("User data failed to update");



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void DeleteUser(User user) {


    }

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
