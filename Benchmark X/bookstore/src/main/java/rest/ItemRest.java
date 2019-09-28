package rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import api.ItemApi;
import rn.ItemRn;

@Path("/item")
public class ItemRest {
	
	@Inject 
	ItemRn itemRn;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemApi> list(@QueryParam("title") String title, @QueryParam("author") String author, 
			@QueryParam("sortPrice") Boolean sorted, @QueryParam("maxPrice") Long maxPrice){
		return itemRn.list(title, author, sorted, maxPrice);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemApi> home(){
		itemRn.home();
		return null;
	}
	
	@GET
	@Path("/best")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemApi> listBestSellers(){
		return itemRn.listBestSellers();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemApi productDetail(@PathParam("id") Long id) {
		return null;
	}

}
