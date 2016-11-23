import bb.rackmesa.wargamescoring.IUserDataAdapter;
import bb.rackmesa.wargamescoring.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by Alex on 11/15/2016.
 */
public class UserDataAdapter implements IUserDataAdapter {
    private String sessionCookie;
    private String url = "http://127.0.0.1:8080/user";
    private HttpClient client = HttpClientBuilder.create().build();

    public UserDataAdapter(){

    }

    @Override
    public User CreateUser(String username, String password, String role, String team, int score){

        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("cookie", sessionCookie);
            post.setHeader("username", username);
            post.setHeader("password", password);
            post.setHeader("role", role);
            post.setHeader("team", team);
            post.setHeader("score", Integer.toString(score));

            HttpResponse response = client.execute(post);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public User RetrieveUser(String username) {
        try {
            HttpGet get = new HttpGet(url);
            get.setHeader("cookie", sessionCookie);
            get.setHeader("username", username);
            HttpResponse response = client.execute(get);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void UpdateUser(User user) {
        try {
            HttpPut put = new HttpPut(url);
            put.setHeader("cookie", sessionCookie);
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
        try {
            HttpDelete delete = new HttpDelete(url);
            delete.setHeader("cookie", sessionCookie);
            delete.setHeader("username", user.getUserName());
            HttpResponse response = client.execute(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void DeleteUser(String username) {
        try {
            HttpDelete delete = new HttpDelete(url);
            delete.setHeader("cookie", sessionCookie);
            delete.setHeader("username", username);
            HttpResponse response = client.execute(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
