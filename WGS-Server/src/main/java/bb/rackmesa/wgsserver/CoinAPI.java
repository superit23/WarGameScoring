package bb.rackmesa.wgsserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.UUID;
import bb.rackmesa.wargamescoring.*;
import org.apache.shiro.subject.Subject;


@Path("/coin")
public class CoinAPI {

    static Logger logger = LogManager.getLogger();

	@POST
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
    //@RequiresRoles("admin")
	public Coin createCoin(@PathParam("username") String username)
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        Coin coin = DatabaseFunctions.CreateCoin(username);
		coin.setInitialUser(username);
        logger.info(subject.getPrincipal().toString() + " has created a coin " + coin.getCoin().toString() + " for user " + username);
		return coin;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @RequiresAuthentication
    //@Path("")
	public ArrayList<Coin> retrieveCoinForUser()
	{
        Subject subject = SecurityUtils.getSubject();

        if(!subject.isAuthenticated())
        {
            throw new AuthenticationException("Subject is not authenticated.");
        }

		String user = subject.getPrincipal().toString();
        logger.info(user + " has retrieved all coins");
		return DatabaseFunctions.RetrieveCoinsForUser(user);
	}
	
	@DELETE
	@Path("{uuid}")
    //@RequiresRoles("admin")
	public void deleteCoin(@PathParam("uuid") String uuid)
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        logger.info(subject.getPrincipal().toString() + " has deleted a coin " + uuid);
		DatabaseFunctions.DeleteCoin(UUID.fromString(uuid));
	}


    @POST
    @Path("/deposit/{username}/{uuid}")
    public void depositCoin(@PathParam("uuid") String uuid, @PathParam("username") String username)
    {
        Coin tCoin = new Coin(uuid);
        tCoin.setSubmitter(username);
        DatabaseFunctions.DepositCoin(tCoin);

        logger.info(SecurityUtils.getSubject().getPrincipal().toString() + " has deposited a coin " + uuid + " for user " + username);
    }
	
	
}
