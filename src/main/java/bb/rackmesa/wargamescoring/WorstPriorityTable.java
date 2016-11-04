package bb.rackmesa.wargamescoring;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dan on 9/22/2016.
 */
public class WorstPriorityTable implements IPriorityTable {

    @Override
    public User prioritize(Coin coin, List<User> users) {
        String initUsername = coin.getInitialUser();

        User initUser = (User)users.stream().filter(user -> user.getUserName().equals(initUsername)).toArray()[0];
        List<User> submitUsers = users.stream().filter(user -> user.getRole().equals(Constants.USER_ROLE)).collect(Collectors.toList());

        boolean existSubmittingUsers = submitUsers.toArray().length > 0;

        if (existSubmittingUsers) {
            List<User> enemyUsers = submitUsers.stream().filter(user -> !user.getUserName().equals(initUsername) && !user.getTeam().equals((initUser.getTeam()))).collect(Collectors.toList());

            // Award enemy users first
            if (enemyUsers.toArray().length > 0) {
                Collections.shuffle(enemyUsers, CryptoFunctions.rand);

                return enemyUsers.get(0);
            }
        }


        List<User> teamUsers = users.stream().filter(user -> user.getRole().equals(Constants.TEAM_ROLE)).collect(Collectors.toList());
        boolean existSubmittingTeams = teamUsers.toArray().length > 0;

        if(existSubmittingTeams) {
            List<User> nonInitUsers = teamUsers.stream().filter(user -> !user.getTeam().equals(initUser.getTeam())).collect(Collectors.toList());

            // Award enemy team second
            if(nonInitUsers.toArray().length > 0)
            {
                Collections.shuffle(nonInitUsers, CryptoFunctions.rand);

                return nonInitUsers.get(0);
            }
        }


        if (existSubmittingUsers) {
            List<User> otherUsers = submitUsers.stream().filter(user -> !user.getUserName().equals(initUsername)).collect(Collectors.toList());

            // Award embezzling team-members first
            if (otherUsers.toArray().length > 0) {
                Collections.shuffle(otherUsers, CryptoFunctions.rand);

                return otherUsers.get(0);
            }
        }

        // Award initial user last
        return initUser;


    }


}
