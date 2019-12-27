package soap;

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
import rn.ItemRn;
import util.EnumSearchType;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class ItemService {
	
	@Inject 
	ItemRn itemRn;
		
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
//	@Path("/home")
	public HomeApi home(@WebParam(name = "token") Long token){
		return itemRn.home(token);
	}
	
	/**
	 * Best Sellers
	 */
	@WebMethod
//	@Path("/best/{subject}")
	public List<ItemApi> bestSellers(@WebParam(name = "subject") String subject){
		return itemRn.bestSellers(subject);
	}
	
	/**
	 * New Products
	 */
	@WebMethod
//	@Path("/new/{subject}")
	public List<ItemApi> newProducts(@WebParam(name = "subject") String subject){
		return itemRn.newProducts(subject);
	}
	
	/**
	 * Product Detail
	 */
	@WebMethod
//	@Path("{id}")
	public ItemApi productDetail(@WebParam(name = "id") Long id) {
		return itemRn.productDetail(id);
	}

}
