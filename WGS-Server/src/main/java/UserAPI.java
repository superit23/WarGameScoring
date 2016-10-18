import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/user")
public class UserAPI {

	@POST
	@Path("{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String createUser(@PathParam("username") String username)
	{
		return "";
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String retrieveUser(@PathParam("username") String username)
	{
		return "";
	}
	
	
	@PUT
	@Path("{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public void updateUser(@PathParam("username") String username)
	{
		
	}
	
	@DELETE
	@Path("{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public void deleteUser(@PathParam("username") String username)
	{
		DatabaseFunctions.DeleteUser(new User(username));
	}
	
}
