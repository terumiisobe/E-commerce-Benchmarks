package rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import api.ItemApi;
import api.OrderApi;
import api.RegistrationApi;
import rn.OrderRn;

@Path("/order")
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
	public Response shoppingCart(@QueryParam("addFlag") Boolean addFlag, List<ItemApi> items) {
		orderRn.shoppingCart(id);
		return null;
	}
	
	/**
	 * Buy Request
	 */
	@POST 
	public Response buyRequest() {
		orderRn.buyRequest();
		return null;
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
