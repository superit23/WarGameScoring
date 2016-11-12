package bb.rackmesa.wargamescoring;

/**
 * Created by Dan on 11/12/2016.
 */
public interface IUserDataAdapter {
    User CreateUser(String username, String password, String role, String team, int score) throws Exception;

    User RetrieveUser(String username);

    void UpdateUser(User user);

    void DeleteUser(User user);

    void DeleteUser(String username);




}
