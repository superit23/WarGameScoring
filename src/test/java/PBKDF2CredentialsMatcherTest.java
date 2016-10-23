import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.AuthorizingSecurityManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dan on 9/13/2016.
 */
public class PBKDF2CredentialsMatcherTest {
    @Test
    public void doCredentialsMatch() throws Exception {
        Configuration.Init();
        Configuration.EasyConf();

        User tUser = DatabaseFunctions.CreateUser("tUser", "tPass", "tRole", "team1", 0);
        UsernamePasswordToken token = new UsernamePasswordToken(tUser.getUserName(), "tPass");

        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        Assert.assertTrue(subject.isAuthenticated());

        subject.logout();
        Assert.assertFalse(subject.isAuthenticated());

        DatabaseFunctions.DeleteUser(tUser);
    }

}