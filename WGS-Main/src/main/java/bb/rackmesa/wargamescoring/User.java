package bb.rackmesa.wargamescoring;

import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.ByteSource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Alex on 9/13/2016.
 */
public class User extends SimpleAccount {
    //private String role;
    private int score;
    private String team;
    private String password;

    public User()
    {
        super("", "", Constants.WGS_REALM);
    }

    public User(String username)
    {
        super(username, "", Constants.WGS_REALM, null, null);
    }

    public String getUserName() {
        return getPrincipals().fromRealm(Constants.WGS_REALM).iterator().next().toString();
    }

    public void setUserName(String userName) {
        getPrincipals().fromRealm(Constants.WGS_REALM).remove(getUserName());
        getPrincipals().fromRealm(Constants.WGS_REALM).add(userName);
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPassword() {
        return getCredentials().toString();
    }

    public void setPassword(String password) {
        //setCredentials(password);
        try{
            byte[] salt = CryptoFunctions.generateSalt(8);
            String derived = Base64.encodeToString(CryptoFunctions.pbkdf2(password.toCharArray(), salt, Configuration.getConfig().pbkdf2Iterations, Configuration.getConfig().pbkdf2NumBytes));
            setCredentials(derived);
            setCredentialsSalt(ByteSource.Util.bytes(salt));
        }
        catch (InvalidKeySpecException ex)
        {

        }
        catch (NoSuchAlgorithmException ex)
        {

        }

    }

    public String getRole() {
        //return role;
        return getRoles().iterator().next();
    }

    public void setRole(String role) {
        //this.role = role;
        Collection<String> roles = getRoles();

        if(roles == null)
        {
            setRoles(new HashSet<>());
        }
        else
        {
            getRoles().clear();
        }

        getRoles().add(role);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;

        User oUser = (User)o;

        if(oUser == null)
            return false;

        return getUserName() == ((User) o).getUserName() && getTeam() == ((User) o).getTeam();
    }
}
