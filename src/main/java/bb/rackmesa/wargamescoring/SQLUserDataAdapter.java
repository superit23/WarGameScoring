package bb.rackmesa.wargamescoring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.ByteSource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Dan on 11/12/2016.
 */
public class SQLUserDataAdapter implements IUserDataAdapter {

    static Logger logger = LogManager.getLogger();

    public User CreateUser(String username, String password, String role, String team, int score) throws InvalidKeySpecException, NoSuchAlgorithmException {

        User newUser = new User(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setScore(score);
        newUser.setTeam(team);

        DatabaseFunctions.Insert("INSERT INTO users (userName, password, salt, role, team, score) VALUES(?, ?, ?, ?, ?, ?);", new Object[]{newUser.getUserName(), newUser.getPassword(), newUser.getCredentialsSalt().toBase64(), newUser.getRole(), newUser.getTeam(), newUser.getScore()});
        return newUser;

    }

    public User RetrieveUser(String username) {
        try {
            ResultSet rs = DatabaseFunctions.Retrieve("SELECT * FROM users WHERE userName = ?;", new Object[]{username});

            if(rs.next()) {
                User user = new User(username);
                user.setCredentials(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setScore(rs.getInt("score"));
                user.setTeam(rs.getString("team"));
                user.setCredentialsSalt(ByteSource.Util.bytes(Base64.decode(rs.getString("salt"))));
                return user;
            }
            else
            {
                logger.error("RetrieveUser: Resultset is empty!");
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public void UpdateUserScore(User user) {
        DatabaseFunctions.Insert("UPDATE users SET score = ? WHERE userName = ?;", new Object[]{user.getScore(), user.getUserName()});
    }

    public void UpdateUserPassword(User user) {
        DatabaseFunctions.Insert("UPDATE users SET password = ?, salt = ? WHERE userName = ?;", new Object[]{user.getPassword(), user.getCredentialsSalt().toBase64(), user.getUserName()});
    }

    public void UpdateUser(User user)
    {
        DatabaseFunctions.Insert("UPDATE users SET password = ?, salt = ?, score = ?, role = ?, team = ? WHERE userName = ?;", new Object[]{user.getPassword(), user.getCredentialsSalt().toBase64(), user.getScore(), user.getRole(), user.getTeam(), user.getUserName()});
    }


    public void DeleteUser(User user)  {
        DeleteUser(user.getUserName());
    }

    public void DeleteUser(String username)
    {
        DatabaseFunctions.Insert("DELETE FROM users WHERE userName = ?;", new Object[]{username});
    }

}
