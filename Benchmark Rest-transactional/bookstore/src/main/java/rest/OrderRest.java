package rest;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.ItemQuantityApi;
import api.OrderApi;
import api.RegistrationApi;
import api.ShoppingCartApi;
import exception.BookstoreException;
import rn.OrderRn;

@Path("/order")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class OrderRest {
	
	@Inject
	OrderRn orderRn;
	
	/**
	 * Customer Registration
	 */
	@POST
	@Path("/register")
	public Response customerRegistration(@QueryParam("returningCustomer") Boolean returningCustomer, 
			@QueryParam("username") String username, @QueryParam("password") String password, RegistrationApi registration) throws BookstoreException {
		return Response.status(200).type(MediaType.APPLICATION_XML).entity(orderRn.customerRegistration(returningCustomer, username, password, registration).toString()).build();
	}
	
	/**
	 * Shopping Cart 
	 */
	@PUT
	public Response shoppingCart(@QueryParam("token") Long token, @QueryParam("addFlag") @DefaultValue("true") Boolean addFlag, ItemQuantityApi item) throws BookstoreException {
		ShoppingCartApi api = orderRn.shoppingCart(token, addFlag, item);
		return Response.status(Response.Status.OK).entity(api).build();
	}
	
	/**
	 * Buy Confirm
	 */
	@POST
	@Path("/confirm")
	public Response buyConfirm(@QueryParam("token") Long token) throws BookstoreException {
		OrderApi api = orderRn.buyConfirm(token);
		return Response.status(Response.Status.OK).entity(api).build();
	}
	
	/**
	 * Order Display
	 */
	@GET
	@Path("/display")
	public OrderApi orderDisplay(@QueryParam("token") Long token) throws BookstoreException {
		return orderRn.displayOrder(token);
	}
	
}
