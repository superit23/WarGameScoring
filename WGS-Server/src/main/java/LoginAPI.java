import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginAPI {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
		return "Hello Jersey";
	}
}
