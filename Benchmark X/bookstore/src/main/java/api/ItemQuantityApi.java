package api;

import api.ItemApi;

public class ItemQuantityApi {
	private ItemApi item;
	private Integer quantity;
	
	public ItemApi getItem() {
		return item;
	}
	public void setItem(ItemApi item) {
		this.item = item;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
