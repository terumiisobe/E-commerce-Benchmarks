package rn;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import api.ItemApi;
import api.OrderApi;
import api.RegistrationApi;
import dao.CustomerDao;
import dao.ItemDao;
import dao.OrderDao;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderLine;
import util.EnumOrderStatus;
import util.ShoppingSession;

public class OrderRn {
	
	@Inject 
	OrderDao orderDao;
	
	@Inject
	CustomerDao customerDao;
	
	@Inject
	ItemDao itemDao;
	
	@Inject
	ShoppingSession shoppingSession;
	
	/**
	 * Register an user by performing log in.
	 */
	public void customerRegistration(Boolean returningCustomer, String username, String password, 
			RegistrationApi registration) {
		if(returningCustomer) {
			Customer customer = customerDao.fetchCustomer(username);
			if(customer.getPassword() != password) {
				return;
			}
			shoppingSession.setCustomerId(customer.getId());
			shoppingSession.setLoginTime(new Date());
		}
		else{
			// Not sure if new customers will be implemented
//			Customer newCustomer = new Customer();
//			// generates random username
//			// generates random password
//			newCustomer.setFullName(registration.getFullName());
//			newCustomer.setAddress(registration.getAddress());
//			newCustomer.setPhoneNumber(registration.getPhoneNumber());
//			newCustomer.setEmail(registration.getEmail());
//			newCustomer.setBirthDate(registration.getBirthDate());
//			customerDao.persistCustomer(newCustomer);
		}
	}
	
	/**
	 * Checks if the user logged in.
	 */
	public boolean userIsLoggedIn() {
		if(shoppingSession.getCustomerId() == null || shoppingSession.getLoginTime() == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Adds product to shopping cart. (*needs registration*)
	 * If the customer doesn't have a pending order, create a new one.
	 */
	public void shoppingCart(Boolean addFlag, List<ItemApi> items) {
		if(!userIsLoggedIn()) {
			return;
		}
		Customer customer = customerDao.searchById(shoppingSession.getCustomerId());
		
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
		Order order = orderDao.fetchOrder(shoppingSession.getCustomerId());
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
	
	/**
	 * Executes algorithm DigSyl.
	 */
	private String generateRandomString(Long d, Integer n) {
		String result = "";
		String[] digits = new String[10];
		digits[0] = "BA";
		digits[1] = "OG";
		digits[2] = "AL";
		digits[3] = "RI";
		digits[4] = "RE";
		digits[5] = "SE";
		digits[6] = "AT";
		digits[7] = "UL";
		digits[8] = "IN";
		digits[9] = "NG";
		char[] dChar = d.toString().toCharArray();
		for(char c : dChar) {
			result += digits[Character.getNumericValue(c)];
		}
		return result;
	}
}
