import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * Created by Dan on 9/13/2016.
 */
public class SimpleDBRealm extends JdbcRealm {

    private static Logger logger = LogManager.getLogger(SimpleDBRealm.class);

    @Override
    protected org.apache.shiro.authc.AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken cToken = (UsernamePasswordToken)token;

        SimpleAccount accnt = DatabaseFunctions.RetrieveUser((String)cToken.getPrincipal());
        return accnt;

        //return super.doGetAuthenticationInfo(token);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = principals.fromRealm("Cerberus").iterator().next().toString();
        return DatabaseFunctions.RetrieveUser(username);

    }

}

