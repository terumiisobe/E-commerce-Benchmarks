package soap;

import java.util.HashMap;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.ws.rs.core.Response;

import api.OrderApi;
import api.RegistrationApi;
import api.ShoppingCartApi;
import exception.BookstoreException;
import rn.OrderRn;

@WebService(name = "order")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class OrderService {
	
	@Inject
	OrderRn orderRn;
	
	/**
	 * Customer Registration
	 */
	@WebMethod
	public Long customerRegistration(@WebParam(name = "returningCustomer") Boolean returningCustomer, 
			@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "registration") RegistrationApi registration) throws BookstoreException {
		return orderRn.customerRegistration(returningCustomer, username, password, registration);
	}
	
	/**
	 * Shopping Cart 
	 */
	@WebMethod
	public ShoppingCartApi shoppingCart(@WebParam(name = "token") Long token, @WebParam(name = "addFlag") Boolean addFlag, @WebParam(name = "item") HashMap<Long, Integer> item) throws BookstoreException {
		return orderRn.shoppingCart(token, addFlag, item);
	}
	
	/**
	 * Buy Confirm
	 */
	@WebMethod 
	public OrderApi buyConfirm(@WebParam(name = "token") Long token) throws BookstoreException {
		return orderRn.buyConfirm(token);
	}
	
	/**
	 * Order Display
	 */
	@WebMethod
	public OrderApi orderDisplay(@WebParam(name = "token") Long token) throws BookstoreException {
		return orderRn.displayOrder(token);
	}
	
}
