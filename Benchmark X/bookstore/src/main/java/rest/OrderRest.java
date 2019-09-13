package rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
		return orderRn.register(username, password);
	}
	
	@PUT
	public Response shoppingCart(@QueryParam("productId") Long id) {
		return null;
	}
	
	@POST 
	public Response buyRequest() {
		return null;
	}
		
	@GET
	public OrderApi orderDisplay() {
		return null;
	}
	
}
