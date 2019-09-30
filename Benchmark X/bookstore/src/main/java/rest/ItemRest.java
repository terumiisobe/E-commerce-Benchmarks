package rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import api.HomeApi;
import api.ItemApi;
import rn.ItemRn;
import util.EnumSearchType;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class ItemRest {
	
	@Inject 
	ItemRn itemRn;
		
	/**
	 * Search
	 */
	@GET
	public List<ItemApi> search(@QueryParam("searchType") EnumSearchType searchType, @QueryParam("searchText") String searchText){
		return itemRn.search(searchType, searchText);
	}
		
	/**
	 * Home
	 */
	@GET
	@Path("/home")
	public HomeApi home(){
		return itemRn.home();
	}
	
	/**
	 * Best Sellers
	 */
	@GET
	@Path("/best/{subject}")
	public List<ItemApi> bestSellers(@PathParam("subject") String subject){
		return itemRn.bestSellers(subject);
	}
	
	/**
	 * New Products
	 */
	@GET
	@Path("/new/{subject}")
	public List<ItemApi> newProducts(@PathParam("subject") String subject){
		return itemRn.newProducts(subject);
	}
	
	/**
	 * Product Detail
	 */
	@GET
	@Path("{id}")
	public ItemApi productDetail(@PathParam("id") Long id) {
		return itemRn.productDetail(id);
	}

}
