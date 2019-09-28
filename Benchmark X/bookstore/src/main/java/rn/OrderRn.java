package rn;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import api.OrderApi;
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
	 * Register an user by performing log in.
	 */
	public void register(String username, String password) {
		Customer customer = customerDao.fetchCustomer(username);
		if(customer.getPassword() == password) {
			userSession.setCustomerId(customer.getId());
			userSession.setLoginTime(new Date());
		}
	}
	
	/**
	 * Checks if the user logged in.
	 */
	public boolean userIsLoggedIn() {
		if(userSession.getCustomerId() == null || userSession.getLoginTime() == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Adds product to shopping cart. (*needs registration*)
	 * If the customer doesn't have a pending order, create a new one.
	 */
	public void shoppingCart(Long productId) {
		if(!userIsLoggedIn()) {
			return;
		}
		Customer customer = customerDao.searchById(userSession.getCustomerId());
		Item item = itemDao.searchById(productId);
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
		} else {
			order.setTotal(order.getTotal().add(item.getCost()));
		}
		OrderLine orderLine = generateOrderLine(order, item);
		orderDao.persistOrder(order);
		orderDao.persistOrderLine(orderLine);
	}
	
	/**
	 * Generates an order line between an order from a customer and an item.
	 */
	private OrderLine generateOrderLine(Order order, Item item) {
		OrderLine orderLine = new OrderLine();
		orderLine.setItem(item);
		orderLine.setOrder(order);
		return orderLine;
	}
	
	/**
	 * Process a buy request by incrementing the timesSold of each item and 
	 * setting the order status to FINISHED.
	 */
	public void buyRequest() {
		if(!userIsLoggedIn()) {
			return;
		}
		Order order = orderDao.fetchOrder(userSession.getCustomerId());
		if(order == null) {
			return;
		}
		List<OrderLine> orderLineList = orderDao.fetchOrderLine(order.getId());
		orderLineList.stream().forEach(ol -> ol.getItem().setTimesSold(ol.getItem().getTimesSold() + 1));
		order.setStatus(EnumOrderStatus.FINISHED);
		orderDao.persistOrder(order);
	}
	
	/**
	 * Displays the order details.
	 */
	public OrderApi displayOrder(Long orderId) {
		Order order = orderDao.searchById(orderId);
		return convertToApi(order);
	}
	
	private OrderApi convertToApi(Order order) {
		OrderApi api = new OrderApi();
		api.setId(order.getId());
		api.setDate(order.getDate());
		api.setStatus(order.getStatus());
		api.setTotal(order.getTotal());
		return api;
	}
}
