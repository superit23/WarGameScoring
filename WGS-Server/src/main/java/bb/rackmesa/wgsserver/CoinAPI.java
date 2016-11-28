package bb.rackmesa.wgsserver;

import bb.rackmesa.wargamescoring.Coin;
import bb.rackmesa.wargamescoring.Configuration;
import bb.rackmesa.wargamescoring.SupportingLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.UUID;


@Path("/coin")
public class CoinAPI {

    static Logger logger = LogManager.getLogger();

    static Configuration configuration = Configuration.getConfig();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Coin createCoin(@HeaderParam("username") String username) throws Exception
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        if(username != null) {
            Coin coin = configuration.coinAdapter.CreateCoin(username);
            coin.setInitialUser(username);
            logger.info(subject.getPrincipal().toString() + " has created a coin " + coin.getCoin().toString() + " for user " + username);
            return coin;
        }
        else
        {
            String[] args = {"'username' cannot be null. Please include the HeaderParam."};
            throw new IllegalArgumentException("'username' cannot be null. Please include the HeaderParam.");
        }
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Coin> retrieveCoinForUser()
	{
        Subject subject = SecurityUtils.getSubject();

        RESTHelper.checkAuth(subject);

		String user = subject.getPrincipal().toString();
        logger.info(user + " has retrieved all coins");
		return configuration.coinAdapter.RetrieveCoinsForUser(user);
	}
	
	@DELETE
	public void deleteCoin(@HeaderParam("uuid") String uuid) throws Exception
	{
        Subject subject = SecurityUtils.getSubject();
        subject.checkRole("admin");

        logger.info(subject.getPrincipal().toString() + " has deleted a coin " + uuid);
        configuration.coinAdapter.DeleteCoin(UUID.fromString(uuid));
	}


    @POST
    @Path("/deposit")
    public void depositCoin(@HeaderParam("uuid") String uuid, @HeaderParam("username") String username)
    {
        Coin tCoin = new Coin(uuid);
        tCoin.setSubmitter(username);
        SupportingLogic.DepositCoin(tCoin);

        logger.info(SecurityUtils.getSubject().getPrincipal().toString() + " has deposited a coin " + uuid + " for user " + username);
    }
	
	
}
