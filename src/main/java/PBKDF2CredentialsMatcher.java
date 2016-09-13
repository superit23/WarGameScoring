/**
 * Created by Dan on 9/8/2016.
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PBKDF2CredentialsMatcher implements CredentialsMatcher {

    private static Logger logger = LoggerFactory.getLogger(PBKDF2CredentialsMatcher.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        SimpleAccount authInfo = (SimpleAccount)authenticationInfo;

        //boolean uNamesMatch = token.getUsername() == authenticationInfo.getPrincipals().fromRealm("Cerberus").iterator().next().toString();
        boolean uNamesMatch = false;
        String derived = null;
        try {
            uNamesMatch = CryptoFunctions.slowEquals(token.getUsername().getBytes(), (authenticationInfo.getPrincipals().fromRealm("Wargame").iterator().next().toString().getBytes()));
            byte[] salt = authInfo.getCredentialsSalt().getBytes();
            derived = Base64.encodeToString(CryptoFunctions.pbkdf2(token.getPassword(), salt, Configuration.pbkdf2Iterations, Configuration.pbkdf2NumBytes));
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());

        }

        boolean passesMatch = CryptoFunctions.slowEquals(derived.getBytes(), ((String)authenticationInfo.getCredentials()).getBytes());
        return passesMatch && uNamesMatch;
    }
}