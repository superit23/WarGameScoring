import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/auth")
public class LoginAPI {

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Session login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
		Subject currSubject = SecurityUtils.getSubject();
		currSubject.login(new UsernamePasswordToken(username, password));


		return currSubject.getSession();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@HeaderParam("username") String username, @HeaderParam("password") String password) {
		SecurityUtils.getSubject().logout();
	}

}
