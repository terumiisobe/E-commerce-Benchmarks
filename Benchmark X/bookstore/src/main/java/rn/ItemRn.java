package rn;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import api.HomeApi;
import api.ItemApi;
import dao.CustomerDao;
import dao.ItemDao;
import model.Customer;
import model.Item;
import util.EnumSearchType;
import util.ShoppingSession;

public class ItemRn {
	
	@Inject 
	ItemDao itemDao;
	
	@Inject
	CustomerDao customerDao;
	
	/**
	 * Searches for item that match the parameters up to 50 items.
	 */
	public List<ItemApi> search(EnumSearchType searchType, String searchText){	
		List<Item> list = itemDao.search(searchType, searchText);
		List<ItemApi> listApi = list.stream().map(i -> convertToApi(i)).collect(Collectors.toList());
		return listApi;
	}
	
	/**
	 * Lists the first 50 items from a subject sorted by ascending title.  
	 */
	public List<ItemApi> bestSellers(String subject){	
		List<Item> list = itemDao.bestSellers(subject);
		List<ItemApi> listApi = list.stream().map(i -> convertToApi(i)).collect(Collectors.toList());		
		return listApi;
	}
	
	/**
	 * Lists the first 50 items from a subject sorted by descending publication date and ascending title.  
	 */
	public List<ItemApi> newProducts(String subject){	
		List<Item> list = itemDao.newProducts(subject);
		List<ItemApi> listApi = list.stream().map(i -> convertToApi(i)).collect(Collectors.toList());
		return listApi;
	}
	
	/**
	 * Lists all subjects for best sellers and new items, and the name of the customer if registered.
	 */
	public HomeApi home(){
		HomeApi api = new HomeApi();
		if(ShoppingSession.getInstance().getCustomerId() != null) {
			Customer customer = customerDao.searchById(ShoppingSession.getInstance().getCustomerId());
			api.setCustomerName(customer.getFullName());
		}
		List<String> subjectList = itemDao.getSubjects();
		api.setBestSellers(subjectList);
		api.setWhatsNew(subjectList);
		return api;
	}
	
	/**
	 * Search for an item detail.
	 */
	public ItemApi productDetail(Long id) {
		Item item = itemDao.productDetail(id);
		return convertToApi(item);
	}
	
	public ItemApi convertToApi(Item item) {
		ItemApi api = new ItemApi();
		api.setId(item.getId());
		api.setTitle(item.getTitle());
		api.setAuthor(item.getAuthor().getFullName());
		api.setSubject(item.getSubject());
		api.setPublicationDate(item.getPublishDate());
		api.setPublisher(item.getPublisher());
		api.setCost(item.getCost());
		api.setAvailability(item.getAvailability());
		return api;
	}
}
