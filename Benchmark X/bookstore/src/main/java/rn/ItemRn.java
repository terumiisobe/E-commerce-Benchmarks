package rn;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import api.ItemApi;
import dao.ItemDao;
import model.Item;
import util.UserSession;

public class ItemRn {
	
	@Inject 
	ItemDao itemDao;
	
	@Inject
	UserSession userSession;
	
	/**
	 * Lists books by title, author, sorted by cheapest price and maximum price limited by 100.
	 */
	public List<ItemApi> list(String title, String author, Boolean sorted, Long maxPrice){	
		List<Item> list = itemDao.list(title, author, sorted, maxPrice);
		List<ItemApi> listApi = list.stream().map(i -> convertToApi(i)).collect(Collectors.toList());
		return listApi;
	}
	
	/**
	 * Lists the 10 best seller books.  
	 */
	public List<ItemApi> listBestSellers(){	
		List<Item> list = itemDao.listBestSellers();
		List<ItemApi> listApi = list.stream().map(i -> convertToApi(i)).collect(Collectors.toList());
		return listApi;
	}
	
	/**
	 * Lists 5 best sellers books and 10 "you may also like" books. 
	 */
	public List<ItemApi> home(){
		if(userSession.getCustomerId() == null) {
			return listBestSellers();
		}
		else {
			return itemDao.listRelatedBooks(userSession.getCustomerId()).stream()
					.map(this::convertToApi).collect(Collectors.toList());
		}
	}
	
	/**
	 * Search for a book description.
	 */
	public ItemApi productDetail(Long id) {
		Item item = itemDao.productDetail(id);
		return convertToApi(item);
	}
	
	private ItemApi convertToApi(Item item) {
		ItemApi api = new ItemApi();
		api.setId(item.getId());
		api.setTitle(item.getTitle());
		api.setAuthor(item.getAuthor().getFullName());
		api.setPublisher(item.getPublisher());
		api.setCost(item.getCost());
		api.setAvailability(item.getAvailability());
		return api;
	}
}
