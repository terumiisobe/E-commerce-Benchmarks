package rn;

import java.util.Date;

import javax.inject.Inject;
import javax.transaction.Transactional;

import dao.CustomerDao;
import dao.ItemDao;
import dao.OrderDao;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderLine;
import util.EnumOrderStatus;
import util.UserSession;

public class OrderRn {
	
	@Inject 
	OrderDao orderDao;
	
	@Inject
	CustomerDao customerDao;
	
	@Inject
	ItemDao itemDao;
	
	@Inject
	UserSession userSession;
	
	/**
	 * Register an user by performing login.
	 */
	public void register(String username, String password) {
		Customer customer = customerDao.fetchCustomer(username);
		if(customer.getPassword() == password) {
			userSession.setCustomerId(customer.getId());
			userSession.setLoginTime(new Date());
		}
	}
	
	/**
	 * Adds product to shopping cart. (*needs registration*)
	 * If the customer doesn't have a pending order, create a new one.
	 */
	@Transactional
	public void shoppingCart(Long idProduct) {
		Customer customer = customerDao.searchById(userSession.getCustomerId());
		Item item = itemDao.searchById(idProduct);
		Order order = orderDao.fetchOrder(customer.getId());
		if(item.getAvailability() < 1) {
			return;
		}
		item.setAvailability(item.getAvailability() - 1);
		if(order == null) {
			order = new Order();
			order.setCustomer(customer);
			order.setDate(new Date());
			order.setStatus(EnumOrderStatus.PENDING);
			order.setTotal(item.getCost());
		}
		OrderLine orderLine = generateOrderLine(order, item);
	}
	
	private OrderLine generateOrderLine(Order order, Item item) {
		OrderLine orderLine = new OrderLine();
		orderLine.setItem(item);
		orderLine.setOrder(order);
		return orderLine;
	}
	
}
