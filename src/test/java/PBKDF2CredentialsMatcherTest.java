import bb.rackmesa.wargamescoring.Configuration;
import bb.rackmesa.wargamescoring.DatabaseFunctions;
import bb.rackmesa.wargamescoring.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

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