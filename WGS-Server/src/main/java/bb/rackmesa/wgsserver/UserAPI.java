package bb.rackmesa.wgsserver;

import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
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
    //@RequiresRoles("admin")
	public User createUser(@HeaderParam("username") String username, @HeaderParam("password") String password, @HeaderParam("role") String role, @HeaderParam("team") String team, @HeaderParam("score") int score) throws Exception
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        logger.info(subject.getPrincipals().asList().get(0).toString() + " created user " + username);
		return DatabaseFunctions.CreateUser(username, password, role, team, score);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    //@RequiresRoles("admin")
	@Path("{username}")
	public User retrieveUser(@PathParam("username") String username)
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        logger.info(subject.getPrincipals().asList().get(0).toString() + " accessed user " + username);
		return DatabaseFunctions.RetrieveUser(username);
	}
	
	
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    //@RequiresAuthentication
	public void updateUser(User user)
	{
        Subject sub = SecurityUtils.getSubject();
        if(!sub.isAuthenticated())
        {
            throw new AuthenticationException("Subject is not authenticated.");
        }

        if(sub.getPrincipal().toString().equals(user.getUserName()) || sub.hasRole("admin"))
        {
            logger.info(SecurityUtils.getSubject().getPrincipals().asList().get(0).toString() + " updated user " + user.getUserName());
            DatabaseFunctions.UpdateUser(user);
        }
        else
        {
            logger.info(SecurityUtils.getSubject().getPrincipals().asList().get(0).toString() + " attempted to update user " + user.getUserName());
        }

	}
	
	@DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    //@RequiresRoles("admin")
	public void deleteUser(User user)
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        logger.info(subject.getPrincipals().asList().get(0).toString() + " deleted user " + user.getUserName());
		DatabaseFunctions.DeleteUser(user);
	}
	
}
