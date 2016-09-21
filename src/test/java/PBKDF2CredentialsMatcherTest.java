import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.AuthorizingSecurityManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dan on 9/13/2016.
 */
public class PBKDF2CredentialsMatcherTest {
    @Test
    public void doCredentialsMatch() throws Exception {
        Main.Init();

        UsernamePasswordToken token = new UsernamePasswordToken("testUser", "testPass");

        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
    }

}