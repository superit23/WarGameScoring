import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.UUID;


@Path("/coin")
public class CoinAPI {

	@POST
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coin createCoin(@PathParam("username") String username)
	{
		return new Coin();
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Coin> retrieveCoinForUser(User user)
	{
		return DatabaseFunctions.RetrieveCoinsForUser(user);
	}
	
	@DELETE
	@Path("{uuid}")
	public void deleteCoin(@PathParam("uuid") String uuid)
	{
		DatabaseFunctions.DeleteCoin(UUID.fromString(uuid));
	}
	
	
	
}
