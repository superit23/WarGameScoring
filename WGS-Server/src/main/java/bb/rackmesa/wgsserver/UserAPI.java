package bb.rackmesa.wgsserver;

import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import bb.rackmesa.wargamescoring.*;

import java.io.UnsupportedEncodingException;


@Path("/user")
public class UserAPI {

    static Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    private static final String NOT_AUTHORIZED_TO_UPDATE = "Current user is not authorized to change %s. Please contact an administrator.";

    static Configuration configuration = Configuration.getConfig();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public User createUser(@HeaderParam("username") String username, @HeaderParam("password") String password, @HeaderParam("role") String role, @HeaderParam("team") String team, @HeaderParam("score") int score) throws Exception
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        User user = configuration.userAdapter.CreateUser(username, password, role, team, score);
        logger.info(subject.getPrincipal().toString() + " created user " + username);
		return user;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User retrieveUser(@HeaderParam("username") String username)
	{
        Subject subject = SecurityUtils.getSubject();

        RESTHelper.checkAuth(subject);

        if(!subject.getPrincipal().toString().equals(username))
        {
            subject.checkRole("admin");
        }


        User user = configuration.userAdapter.RetrieveUser(username);
        logger.info(subject.getPrincipal().toString() + " accessed user " + username);
		return user;
	}
	
	
	@PUT
    @Consumes(MediaType.TEXT_PLAIN)
	public void updateUser(@HeaderParam("username") String username, @HeaderParam("password") String password, @HeaderParam("role") String role, @HeaderParam("team") String team, @HeaderParam("score") int score)
	{
        Subject subject = SecurityUtils.getSubject();
        String subUsername = subject.getPrincipal().toString();

        RESTHelper.checkAuth(subject);

        boolean isUser = subUsername.equals(username);

        if(isUser || subject.hasRole("admin"))
        {
            User user = new User(username);
            User userFromDB = configuration.userAdapter.RetrieveUser(username);

            if(!subject.hasRole("admin")) {
                if (role != null && !RESTHelper.CompareUncleanStrings(userFromDB.getRole(), role)) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException(String.format(NOT_AUTHORIZED_TO_UPDATE, "role"));
                }
                else if (team != null && !RESTHelper.CompareUncleanStrings(userFromDB.getTeam(), team)) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException(String.format(NOT_AUTHORIZED_TO_UPDATE, "team"));
                }
                else if (score != 0 && userFromDB.getScore() != score) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException(String.format(NOT_AUTHORIZED_TO_UPDATE, "score"));
                }
                else
                {
                    user.setTeam(userFromDB.getTeam());
                    user.setScore(userFromDB.getScore());
                    user.setRole(userFromDB.getRole());
                }

            }
            else
            {
                user.setRole(role);
                user.setTeam(team);
                user.setScore(score);
            }

            user.setPassword(password);


            configuration.userAdapter.UpdateUser(user);
            logger.info(subUsername + " updated user " + username);
        }
        else
        {
            logger.info(subUsername + " failed to update user " + username);
        }

	}
	
	@DELETE
    @Consumes(MediaType.TEXT_PLAIN)
	public void deleteUser(@HeaderParam("username") String username)
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        configuration.userAdapter.DeleteUser(username);
        logger.info(subject.getPrincipal().toString() + " deleted user " + username);
	}


	
}
