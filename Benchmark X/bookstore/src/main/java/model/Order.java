package model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import util.EnumOrderStatus;

@Entity
public class Order {
	
	@Id
	@GeneratedValue
	private Long id;
	private Customer customer;
	private Date date;
	private BigDecimal total;
	private EnumOrderStatus status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
