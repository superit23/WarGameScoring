import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dan on 9/22/2016.
 */
public class WorstPriorityTable implements IPriorityTable {
    @Override
    public User prioritize(Coin coin, List<User> users) {
        String initUsername = coin.getInitialUser();

        User initUser = (User)users.stream().filter(user -> user.getUserName().equals(initUsername)).toArray()[0];
        User[] submitUsers = (User[])users.stream().filter(user -> user.getRole().equals(Constants.USER_ROLE)).toArray();

        boolean existSubmittingUsers = submitUsers.length > 0;

        if (existSubmittingUsers) {
            User[] enemyUsers = (User[]) Arrays.stream(submitUsers).filter(user -> !user.getUserName().equals(initUsername) && !user.getTeam().equals((initUser.getTeam()))).toArray();

            // Award enemy users first
            if (enemyUsers.length > 0) {
                List<User> userList = Arrays.asList(enemyUsers);
                Collections.shuffle(userList, Constants.rand);

                return userList.get(0);
            }
        }


        User[] teamUsers = (User[])users.stream().filter(user -> user.getRole().equals(Constants.TEAM_ROLE)).toArray();
        boolean existSubmittingTeams = teamUsers.length > 0;

        if(existSubmittingTeams) {
            User[] nonInitUsers = (User[]) Arrays.stream(submitUsers).filter(user -> !user.getTeam().equals(initUser.getTeam())).toArray();

            // Award enemy team second
            if(nonInitUsers.length > 0)
            {
                List<User> userList = Arrays.asList(nonInitUsers);
                Collections.shuffle(userList, Constants.rand);

                return userList.get(0);
            }
        }


        if (existSubmittingUsers) {
            User[] otherUsers = (User[]) Arrays.stream(submitUsers).filter(user -> !user.getUserName().equals(initUsername)).toArray();

            // Award embezzling team-members first
            if (otherUsers.length > 0) {
                List<User> userList = Arrays.asList(otherUsers);
                Collections.shuffle(userList, Constants.rand);

                return userList.get(0);
            }
        }

        // Award initial user last
        return initUser;


    }
}
