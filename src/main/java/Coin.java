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

    public Coin()
    {

    }

    public Coin(String uuid)
    {
        coin = UUID.fromString(uuid);
    }

    public String getInitialUser() {
        return initialUser;
    }
    public void setInitialUser(String initialUser) {
        this.initialUser = initialUser;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return ((Coin)obj).getCoin().toString().equals(getCoin().toString());
    }

    @Override
    public int hashCode() {
    {
        return getCoin().hashCode();
    }
    }

}
