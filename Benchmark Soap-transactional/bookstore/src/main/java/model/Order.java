package model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import util.EnumOrderStatus;

@Entity
@Table(name = "order_register")
public class Order {
	
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	private Date date;
	@NotNull
	private BigDecimal totalPrice;
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
		return totalPrice;
	}
	public void setTotal(BigDecimal total) {
		this.totalPrice = total;
	}
	public EnumOrderStatus getStatus() {
		return status;
	}
	public void setStatus(EnumOrderStatus status) {
		this.status = status;
	}
}
