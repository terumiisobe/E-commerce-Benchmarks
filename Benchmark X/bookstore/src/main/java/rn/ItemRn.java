package rn;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import api.ItemApi;
import dao.ItemDao;
import model.Item;

public class ItemRn {
	
	@Inject 
	ItemDao itemDao;
	
	/**
	 * Lists products by title, author, sorted by cheapest price and maximum price.
	 */
	public List<ItemApi> list(String title, String author, Boolean sorted, Long maxPrice){	
		List<Item> list = itemDao.list(title, author, sorted, maxPrice);
		List<ItemApi> listApi = list.stream().map(i -> convertToApi(i)).collect(Collectors.toList());
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
