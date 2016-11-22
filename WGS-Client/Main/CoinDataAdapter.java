import bb.rackmesa.wargamescoring.Coin;
import bb.rackmesa.wargamescoring.ICoinDataAdapter;
import bb.rackmesa.wargamescoring.User;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Alex on 11/15/2016.
 */
public class CoinDataAdapter implements ICoinDataAdapter {

    private String sessionCookie;

    @Override
    public Coin CreateCoin(String initialUser) {
        HttpClient client = HttpClientBuilder.create().build();


        return null;
    }

    @Override
    public Coin RetrieveCoin(String uuid) {
        return null;
    }

    @Override
    public ArrayList<Coin> RetrieveCoinsForUser(User user) {
        return null;
    }

    @Override
    public ArrayList<Coin> RetrieveCoinsForUser(String username) {
        return null;
    }

    @Override
    public void DeleteCoin(UUID id) {

    }
}
