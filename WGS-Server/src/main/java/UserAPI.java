import org.apache.shiro.authz.annotation.RequiresRoles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/user")
public class UserAPI {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public User createUser(@HeaderParam("username") String username, @HeaderParam("password") String password, @HeaderParam("role") String role, @HeaderParam("team") String team) throws Exception
	{
		return DatabaseFunctions.CreateUser(username, password, role, team, 0);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public User retrieveUser(String username)
	{
		return DatabaseFunctions.RetrieveUser(username);
	}
	
	
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public void updateUser(User user)
	{
		DatabaseFunctions.UpdateUser(user);
	}
	
	@DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public void deleteUser(User user)
	{
		DatabaseFunctions.DeleteUser(user);
	}
	
}
