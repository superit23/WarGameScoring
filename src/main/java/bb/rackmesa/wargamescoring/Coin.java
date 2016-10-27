package bb.rackmesa.wargamescoring;

import java.util.UUID;

/**
 * Created by Alex on 9/13/2016.
 */
public class Coin {
    private UUID coin = UUID.randomUUID();
    private String initialUser;
    private String submitter;



    public Coin()
    {

    }

    public Coin(String uuid)
    {
        coin = UUID.fromString(uuid);
    }

    public UUID getCoin() {
        return coin;
    }

    public String getInitialUser() {
        return initialUser;
    }

    public void setInitialUser(String initialUser) {
        this.initialUser = initialUser;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
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
