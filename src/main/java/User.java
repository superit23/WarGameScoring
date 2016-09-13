import org.apache.shiro.authc.SimpleAccount;

/**
 * Created by Alex on 9/13/2016.
 */
public class User extends SimpleAccount {
    private String userName;
    private String password;
    private int score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
