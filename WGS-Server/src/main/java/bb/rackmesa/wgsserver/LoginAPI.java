package bb.rackmesa.wgsserver;

import org.apache.logging.log4j.LogManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/auth")
public class LoginAPI {

	static Logger logger = LogManager.getLogger();

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Session login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
		Subject currSubject = SecurityUtils.getSubject();
		currSubject.login(new UsernamePasswordToken(username, password));

		String callingUser = SecurityUtils.getSubject().getPrincipals().asList().get(0).toString();

		if(currSubject.isAuthenticated()) {
			logger.info(callingUser + "logged in");
			return currSubject.getSession();
		}
		else
		{
			logger.info(callingUser + "has failed to log in");
			return null;
		}
	}

	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@HeaderParam("username") String username, @HeaderParam("password") String password) {
		logger.info(SecurityUtils.getSubject().getPrincipals().asList().get(0).toString() + "has logged out");
		SecurityUtils.getSubject().logout();
	}

}
