package bb.rackmesa.wargamescoring;

import java.util.List;

/**
 * Created by Dan on 9/8/2016.
 */
public interface IPriorityTable {

    User prioritize(Coin coin, List<User> users);
}
