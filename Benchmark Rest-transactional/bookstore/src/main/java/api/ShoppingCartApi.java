package api;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShoppingCartApi {
	private List<ItemQuantityApi> cart;
	private BigDecimal totalPrice;
	
	public List<ItemQuantityApi> getCart() {
		return cart;
	}
	public void setCart(List<ItemQuantityApi> cart) {
		this.cart = cart;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
}
