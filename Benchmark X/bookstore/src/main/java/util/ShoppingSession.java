package util;

import java.util.Date;
import java.util.List;

public class ShoppingSession {
	private static ShoppingSession instance;
	private Long customerId;
	private Date loginTime;
	private List<ItemQuantity> cart;
	
	public static ShoppingSession getInstance() {
		if(instance == null) {
			instance = new ShoppingSession();
		}
		return instance;
	}
	public List<ItemQuantity> getCart() {
		return cart;
	}
	public void setCart(List<ItemQuantity> cart) {
		this.cart = cart;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
}
