import bb.rackmesa.wargamescoring.IUserDataAdapter;
import bb.rackmesa.wargamescoring.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by Alex on 11/15/2016.
 */
public class UserDataAdapter implements IUserDataAdapter {
    private String sessionCookie;
    private String url = "http://127.0.0.1:8080/";

    public UserDataAdapter(){

    }

    @Override
    public User CreateUser(String username, String password, String role, String team, int score){

        HttpResponse response = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url + "user");
            post.setHeader("cookie", sessionCookie);
            post.setHeader("username", username);
            post.setHeader("password", password);
            post.setHeader("role", role);
            post.setHeader("team", team);
            post.setHeader("score", Integer.toString(score));

            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(response);
        return null;
    }

    @Override
    public User RetrieveUser(String username) {
        return null;
    }

    @Override
    public void UpdateUser(User user) {

    }

    @Override
    public void DeleteUser(User user) {

    }

    @Override
    public void DeleteUser(String username) {

    }
}
