package rest;

import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import api.OrderApi;
import api.RegistrationApi;
import api.ShoppingCartApi;
import rn.OrderRn;

@Path("/order")
@Produces("application/json")
public class OrderRest {
	
	@Inject
	OrderRn orderRn;
	
	/**
	 * Customer Registration
	 */
	@POST
	@Path("/register")
	public Response customerRegistration(@QueryParam("returningCustomer") Boolean returningCustomer, 
			@QueryParam("username") String username, @QueryParam("password") String password, RegistrationApi registration) {
		orderRn.customerRegistration(returningCustomer, username, password, registration);
		return Response.status(Response.Status.OK).build();
	}
	
	/**
	 * Shopping Cart
	 */
	@PUT
	public Response shoppingCart(@QueryParam("addFlag") Boolean addFlag, HashMap<Long, Integer> item) {
		ShoppingCartApi api = orderRn.shoppingCart(addFlag, item);
		return Response.status(Response.Status.OK).entity(api).build();
	}
	
	/**
	 * Buy Confirm
	 */
	@POST 
	public Response buyConfirm() {
		OrderApi api = orderRn.buyConfirm();
		return Response.status(Response.Status.OK).entity(api).build();
	}
	
	/**
	 * Order Display
	 */
	@GET
	@Path("{id}")
	public OrderApi orderDisplay(@PathParam("id") Long orderId) {
		orderRn.displayOrder(orderId);
		return null;
	}
	
}
