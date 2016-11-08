package bb.rackmesa.wgsserver;

import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
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

        User user = DatabaseFunctions.CreateUser(username, password, role, team, score);
        logger.info(subject.getPrincipal().toString() + " created user " + username);
		return user;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    //@RequiresRoles("admin")
	//@Path("{username}")
	public User retrieveUser(@HeaderParam("username") String username)
	{
        Subject subject = SecurityUtils.getSubject();

        if(!subject.isAuthenticated()) {
            throw new AuthenticationException("Subject is not authenticated.");
        }

        if(!subject.getPrincipal().toString().equals(username))
        {
            subject.checkRole("admin");
        }


        User user = DatabaseFunctions.RetrieveUser(username);
        logger.info(subject.getPrincipal().toString() + " accessed user " + username);
		return user;
	}
	
	
	@PUT
    @Consumes(MediaType.TEXT_PLAIN)
    //@RequiresAuthentication
	public void updateUser(@HeaderParam("username") String username, @HeaderParam("password") String password, @HeaderParam("role") String role, @HeaderParam("team") String team, @HeaderParam("score") int score)
	{
        Subject sub = SecurityUtils.getSubject();
        String subUsername = sub.getPrincipal().toString();

        if(!sub.isAuthenticated())
        {
            throw new AuthenticationException("Subject is not authenticated.");
        }

        boolean isUser = subUsername.equals(username);

        if(isUser || sub.hasRole("admin"))
        {
            User user = new User(username);
            User userFromDB = DatabaseFunctions.RetrieveUser(username);

            if(!sub.hasRole("admin")) {
                if (role != null && userFromDB.getRole().equals(role)) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException("Current user is not authorized to change role. Please contact an administrator.");
                }
                else if (team != null && userFromDB.getTeam().equals(team)) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException("Current user is not authorized to change team. Please contact an administrator.");
                }
                else if (score != 0 && userFromDB.getScore() != score) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException("Current user is not authorized to change score. Please contact an administrator.");
                }

                user.setTeam(userFromDB.getTeam());
                user.setScore(userFromDB.getScore());
                user.setRole(userFromDB.getRole());
            }
            else
            {
                user.setRole(role);
                user.setTeam(team);
                user.setScore(score);
            }

            user.setPassword(password);


            DatabaseFunctions.UpdateUser(user);
            logger.info(subUsername + " updated user " + username);
        }
        else
        {
            logger.info(subUsername + " failed to update user " + username);
        }

	}
	
	@DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    //@RequiresRoles("admin")
	public void deleteUser(@HeaderParam("username") String username)
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

		DatabaseFunctions.DeleteUser(username);
        logger.info(subject.getPrincipal().toString() + " deleted user " + username);
	}
	
}
