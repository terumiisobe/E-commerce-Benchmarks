package api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderApi {
	
	private Long id;
	private Date date;
	private BigDecimal totalPrice;
	private List<ItemQuantityApi> items;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal total) {
		this.totalPrice = total;
	}
	public List<ItemQuantityApi> getItems() {
		return items;
	}
	public void setItems(List<ItemQuantityApi> items) {
		this.items = items;
	}	
}
