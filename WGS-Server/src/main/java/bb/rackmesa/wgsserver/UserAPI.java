package bb.rackmesa.wgsserver;

import bb.rackmesa.wargamescoring.Configuration;
import bb.rackmesa.wargamescoring.User;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


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
	public void updateUser(@HeaderParam("username") String username, @HeaderParam("password") String password, @HeaderParam("credentials") String credentials, @HeaderParam("salt") String salt, @HeaderParam("role") String role, @HeaderParam("team") String team, @HeaderParam("score") int score) throws Exception
	{
        Subject subject = SecurityUtils.getSubject();
        String subUsername = subject.getPrincipal().toString();

        RESTHelper.checkAuth(subject);

        boolean isUser = subUsername.equals(username);

        if(isUser || subject.hasRole("admin"))
        {
            User user = new User(username);
            User userFromDB = configuration.userAdapter.RetrieveUser(username);

            boolean roleNotNull = role != null;
            boolean teamNotNull = role != null;
            boolean scoreNotZero = score != 0;

            if(!subject.hasRole("admin")) {

                if (roleNotNull && !RESTHelper.CompareUncleanStrings(userFromDB.getRole(), role)) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException(String.format(NOT_AUTHORIZED_TO_UPDATE, "role"));
                }
                else if (teamNotNull && !RESTHelper.CompareUncleanStrings(userFromDB.getTeam(), team)) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException(String.format(NOT_AUTHORIZED_TO_UPDATE, "team"));
                }
                else if (scoreNotZero && userFromDB.getScore() != score) {
                    logger.info(subUsername + " failed to update user " + username);
                    throw new AuthorizationException(String.format(NOT_AUTHORIZED_TO_UPDATE, "score"));
                }
                else
                {
                    if(teamNotNull) {
                        user.setTeam(userFromDB.getTeam());
                    }

                    if(scoreNotZero) {
                        user.setScore(userFromDB.getScore());
                    }

                    if(roleNotNull) {
                        user.setRole(userFromDB.getRole());
                    }
                }

            }
            else
            {
                if(roleNotNull) {
                    user.setRole(role);
                }
                else
                {
                    user.setRole(userFromDB.getRole());
                }

                if(teamNotNull) {
                    user.setTeam(team);
                }
                else
                {
                    user.setTeam(userFromDB.getTeam());
                }

                if(scoreNotZero) {
                    user.setScore(score);
                }
                else
                {
                    user.setScore(userFromDB.getScore());
                }
            }

            if(password != null) {
                user.setPassword(password);
            }
            else if(credentials != null && salt != null)
            {
                user.setCredentials(credentials);
                user.setCredentialsSalt(ByteSource.Util.bytes(Base64.decode(salt)));
            }
            else
            {
                user.setCredentials(userFromDB.getCredentials());
                user.setCredentialsSalt(userFromDB.getCredentialsSalt());
            }


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
	public void deleteUser(@HeaderParam("username") String username) throws Exception
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        configuration.userAdapter.DeleteUser(username);
        logger.info(subject.getPrincipal().toString() + " deleted user " + username);
	}


	
}
