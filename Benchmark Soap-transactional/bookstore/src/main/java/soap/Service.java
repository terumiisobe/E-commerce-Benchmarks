package soap;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import api.HomeApi;
import api.ItemApi;
import api.OrderApi;
import api.RegistrationApi;
import api.ShoppingCartApi;
import exception.BookstoreException;
import rn.ItemRn;
import rn.OrderRn;
import util.EnumSearchType;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class Service {
	
	@Inject 
	ItemRn itemRn;
	
	@Inject
	OrderRn orderRn;
		
	/**
	 * Search
	 */
	@WebMethod
	public List<ItemApi> search(@WebParam(name = "searchType") EnumSearchType searchType, @WebParam(name = "searchText") String searchText){
		return itemRn.search(searchType, searchText);
	}
		
	/**
	 * Home
	 */
	@WebMethod
	public HomeApi home(@WebParam(name = "token") Long token){
		return itemRn.home(token);
	}
	
	/**
	 * Best Sellers
	 */
	@WebMethod
	public List<ItemApi> bestSellers(@WebParam(name = "subject") String subject){
		return itemRn.bestSellers(subject);
	}
	
	/**
	 * New Products
	 */
	@WebMethod
	public List<ItemApi> newProducts(@WebParam(name = "subject") String subject){
		return itemRn.newProducts(subject);
	}
	
	/**
	 * Product Detail
	 */
	@WebMethod
	public ItemApi productDetail(@WebParam(name = "id") Long id) {
		return itemRn.productDetail(id);
	}
	
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
