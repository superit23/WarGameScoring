import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.UUID;


@Path("/coin")
public class CoinAPI {

    static Logger logger = LogManager.getLogger();

	@POST
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public Coin createCoin(@PathParam("username") String username)
	{
        Coin coin = new Coin();
        logger.info(((User) SecurityUtils.getSubject()).getUserName() + "has created a coin " + coin.getCoin().toString());
		return coin;
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    @RequiresAuthentication
	public ArrayList<Coin> retrieveCoinForUser()
	{
        logger.info(((User) SecurityUtils.getSubject()).getUserName() + "has retrieved all coins");
		return DatabaseFunctions.RetrieveCoinsForUser((User)SecurityUtils.getSubject());
	}
	
	@DELETE
	@Path("{uuid}")
    @RequiresRoles("admin")
	public void deleteCoin(@PathParam("uuid") String uuid)
	{
        logger.info(((User) SecurityUtils.getSubject()).getUserName() + "has deleted a coin " + uuid);
		DatabaseFunctions.DeleteCoin(UUID.fromString(uuid));
	}


    @POST
    @Path("/deposit/{username}/{uuid}")
    public void depositCoin(@PathParam("uuid") String uuid, @PathParam("username") String username)
    {
        Coin tCoin = new Coin(uuid);
        tCoin.setSubmitter(username);
        DatabaseFunctions.DepositCoin(tCoin);

        logger.info(((User) SecurityUtils.getSubject()).getUserName() + "has deposited a coin " + uuid);
    }
	
	
}
