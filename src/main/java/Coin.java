import java.util.UUID;

/**
 * Created by Alex on 9/13/2016.
 */
public class Coin {
    private UUID coin = UUID.randomUUID();
    private String initialUser;

    public UUID getCoin() {
        return coin;
    }

    public String getInitialUser() {
        return initialUser;
    }
    public void setInitialUser(String initialUser) {
        this.initialUser = initialUser;
    }
}
