package bb.rackmesa.wgsserver;

import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import bb.rackmesa.wargamescoring.*;


@Path("/user")
public class UserAPI {

    static Logger logger = org.apache.logging.log4j.LogManager.getLogger();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public User createUser(@HeaderParam("username") String username, @HeaderParam("password") String password, @HeaderParam("role") String role, @HeaderParam("team") String team, @HeaderParam("score") int score) throws Exception
	{
        logger.info(((User)SecurityUtils.getSubject()).getUserName() + " created user " + username);
		return DatabaseFunctions.CreateUser(username, password, role, team, score);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public User retrieveUser(String username)
	{
        logger.info(((User)SecurityUtils.getSubject()).getUserName() + " accessed user " + username);
		return DatabaseFunctions.RetrieveUser(username);
	}
	
	
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RequiresAuthentication
	public void updateUser(User user)
	{
        Subject sub = SecurityUtils.getSubject();

        if(sub.equals(user) || sub.hasRole("admin"))
        {
            logger.info(((User)SecurityUtils.getSubject() ).getUserName() + " updated user " + user.getUserName());
            DatabaseFunctions.UpdateUser(user);
        }
        else
        {
            logger.info(((User)SecurityUtils.getSubject()).getUserName() + " attempted to update user " + user.getUserName());
        }

	}
	
	@DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public void deleteUser(User user)
	{
        logger.info(((User)SecurityUtils.getSubject()).getUserName() + " deleted user " + user.getUserName());
		DatabaseFunctions.DeleteUser(user);
	}
	
}
