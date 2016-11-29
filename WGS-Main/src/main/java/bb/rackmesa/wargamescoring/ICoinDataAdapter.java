package bb.rackmesa.wargamescoring;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Dan on 11/12/2016.
 */
public interface ICoinDataAdapter {

     Coin CreateCoin(String initialUser) throws Exception;

     Coin RetrieveCoin(String uuid);

     ArrayList<Coin> RetrieveCoinsForUser(User user);

     ArrayList<Coin> RetrieveCoinsForUser(String username);

    void DeleteCoin(UUID id) throws Exception;
}
