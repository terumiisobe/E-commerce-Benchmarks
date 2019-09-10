package api;

import java.math.BigDecimal;
import java.util.Date;

import util.EnumOrderStatus;

public class OrderApi {
	
	private Long id;
	private Date date;
	private BigDecimal total;
	private EnumOrderStatus status;
	
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
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public EnumOrderStatus getStatus() {
		return status;
	}
	public void setStatus(EnumOrderStatus status) {
		this.status = status;
	}	
}
