import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/coin")
public class CoinAPI {

	@POST
	@Path("{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String createCoin(@PathParam("username") String username)
	{
		return "";
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String retrieveCoinForUser(@PathParam("username") String username)
	{
		return "";
	}
	
	@DELETE
	@Path("{uuid}")
	@Produces(MediaType.TEXT_PLAIN)
	public void deleteCoin(@PathParam("uuid") String uuid)
	{
		
	}
	
	
	
}
