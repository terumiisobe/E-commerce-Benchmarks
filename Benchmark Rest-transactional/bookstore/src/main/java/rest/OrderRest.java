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
import exception.BookstoreException;
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
	public Long customerRegistration(@QueryParam("returningCustomer") Boolean returningCustomer, 
			@QueryParam("username") String username, @QueryParam("password") String password, RegistrationApi registration) throws BookstoreException {
		return orderRn.customerRegistration(returningCustomer, username, password, registration);
	}
	
	/**
	 * Shopping Cart 
	 */
	@PUT
	public Response shoppingCart(@QueryParam("token") Long token, @QueryParam("addFlag") Boolean addFlag, HashMap<Long, Integer> item) throws BookstoreException {
		ShoppingCartApi api = orderRn.shoppingCart(token, addFlag, item);
		return Response.status(Response.Status.OK).entity(api).build();
	}
	
	/**
	 * Buy Confirm
	 */
	@POST 
	public Response buyConfirm(@QueryParam("token") Long token) throws BookstoreException {
		OrderApi api = orderRn.buyConfirm(token);
		return Response.status(Response.Status.OK).entity(api).build();
	}
	
	/**
	 * Order Display
	 */
	@GET
	public OrderApi orderDisplay(@QueryParam("token") Long token) throws BookstoreException {
		orderRn.displayOrder(token);
		return null;
	}
	
}