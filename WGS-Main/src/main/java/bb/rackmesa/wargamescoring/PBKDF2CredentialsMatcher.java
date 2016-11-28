package bb.rackmesa.wargamescoring; /**
 * Created by Dan on 9/8/2016.
 */

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
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
        User authInfo = (User)authenticationInfo;

        Configuration configuration = Configuration.getConfig();

        boolean usernamesMatch = false;
        String derived = null;
        try {
            usernamesMatch = CryptoFunctions.slowEquals(token.getUsername().getBytes(), authInfo.getUserName().getBytes());
            byte[] salt = authInfo.getCredentialsSalt().getBytes();
            derived = Base64.encodeToString(CryptoFunctions.pbkdf2(token.getPassword(), salt, configuration.pbkdf2Iterations, configuration.pbkdf2NumBytes));
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());

        }

        boolean passesMatch = CryptoFunctions.slowEquals(derived.getBytes(), authInfo.getPassword().getBytes());
        return passesMatch & usernamesMatch;
    }
}