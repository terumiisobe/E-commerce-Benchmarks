package rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import api.OrderApi;
import rn.OrderRn;

@Path("/order")
public class OrderRest {
	
	@Inject
	OrderRn orderRn;
	
	@POST
	@Path("/register")
	public Response register(@QueryParam("username") String username, @QueryParam("password") String password) {
		orderRn.register(username, password);
		return null;
	}
	
	@PUT
	public Response shoppingCart(@QueryParam("productId") Long id) {
		orderRn.shoppingCart(id);
		return null;
	}
	
	@POST 
	public Response buyRequest() {
		orderRn.buyRequest();
		return null;
	}
		
	@GET
	@Path("{id}")
	public OrderApi orderDisplay(@PathParam("id") Long orderId) {
		orderRn.displayOrder(orderId);
		return null;
	}
	
}
