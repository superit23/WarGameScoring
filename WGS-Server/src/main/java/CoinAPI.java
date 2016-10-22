import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.UUID;


@Path("/coin")
public class CoinAPI {

	@POST
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
    @RequiresRoles("admin")
	public Coin createCoin(@PathParam("username") String username)
	{
		return new Coin();
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    @RequiresAuthentication
	public ArrayList<Coin> retrieveCoinForUser(User user)
	{
		return DatabaseFunctions.RetrieveCoinsForUser(user);
	}
	
	@DELETE
	@Path("{uuid}")
    @RequiresRoles("admin")
	public void deleteCoin(@PathParam("uuid") String uuid)
	{
		DatabaseFunctions.DeleteCoin(UUID.fromString(uuid));
	}


    @POST
    @Path("/deposit/{username}/{uuid}")
    public void depositCoin(@PathParam("uuid") String uuid, @PathParam("username") String username)
    {
        Coin tCoin = new Coin(uuid);
        tCoin.setSubmitter(username);
        DatabaseFunctions.DepositCoin(tCoin);
    }
	
	
}
