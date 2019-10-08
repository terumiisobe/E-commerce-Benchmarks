package rn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import api.ItemQuantityApi;
import api.OrderApi;
import api.RegistrationApi;
import api.ShoppingCartApi;
import dao.CustomerDao;
import dao.ItemDao;
import dao.OrderDao;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderLine;
import util.EnumOrderStatus;
import util.ItemQuantity;
import util.ShoppingSession;

public class OrderRn {
	
	@Inject 
	OrderDao orderDao;
	
	@Inject
	CustomerDao customerDao;
	
	@Inject
	ItemRn itemRn;
	
	@Inject
	ItemDao itemDao;
	
//	@Inject
//	ShoppingSession shoppingSession;
	
	/**
	 * Register an user by performing log in.
	 */
	public void customerRegistration(Boolean returningCustomer, String username, String password, 
			RegistrationApi registration) {
		ShoppingSession shoppingSession = ShoppingSession.getInstance();
		if(returningCustomer) {
			Customer customer = customerDao.fetchCustomer(username);
			if(customer == null || !customer.getPassword().equals(password)) {
				return;
			}
			shoppingSession.setCustomerId(customer.getId());
			shoppingSession.setLoginTime(new Date());
			shoppingSession.setCart(new ArrayList<>());
		}
		else{
			// TODO: decide if new customers will be implemented
			Customer newCustomer = new Customer();
			newCustomer.setUsername(Long.toString(System.currentTimeMillis()));
			newCustomer.setPassword("password");
			newCustomer.setFullName(registration.getFullName());
			newCustomer.setAddress(registration.getAddress());
			newCustomer.setPhoneNumber(registration.getPhoneNumber());
			newCustomer.setEmail(registration.getEmail());
			newCustomer.setBirthDate(registration.getBirthDate());
			customerDao.persistCustomer(newCustomer);
		}
	}
	
	/**
	 * Checks if the user logged in.
	 */
	public boolean userIsLoggedIn() {
		if(ShoppingSession.getInstance().getCustomerId() == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Adds product to shopping cart. 
	 * (*needs registration*)
	 */
	public ShoppingCartApi shoppingCart(Boolean addFlag, HashMap<Long, Integer> items) {
		if(!userIsLoggedIn()) {
			return null;
		}
		// if ADD and the cart is not full
		if(addFlag && ShoppingSession.getInstance().getCart().size() < 100) {
			for(Long itemId : items.keySet()) {
				if(itemDao.searchById(itemId).getAvailability() <= 0) {
					return null;
				}
				List<ItemQuantity> list = ShoppingSession.getInstance().getCart();
				Boolean itemExist = false;
				for(ItemQuantity iq : list) {
					// if item already exists in cart
					if(iq.getItem().getId() == itemId) {
						iq.setQuantity(items.get(itemId));
						itemExist = true;
					}
				}
				//if item doesn't exist in cart
				if(!itemExist) {
					ItemQuantity newItem = new ItemQuantity();
					newItem.setItem(itemDao.searchById(itemId));
					newItem.setQuantity(items.get(itemId));
					ShoppingSession.getInstance().getCart().add(newItem);
				}
			}
		}
		ShoppingCartApi api = new ShoppingCartApi();
		List<ItemQuantity> cart = ShoppingSession.getInstance().getCart();
		api.setCart(convertToApi(cart));
		api.setTotalPrice(calculateTotalPrice(cart));
		return api;
	}
	
	/**
	 * Calculates total price in shopping cart.
	 */
	private BigDecimal calculateTotalPrice(List<ItemQuantity> cart) {
		BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal price = new BigDecimal(0);
		for(ItemQuantity iq : ShoppingSession.getInstance().getCart()) {
			price = iq.getItem().getCost().multiply(new BigDecimal(iq.getQuantity())); 
			totalPrice = totalPrice.add(price);
		}
		return totalPrice;
	}
	
	/**
	 * Converts List<ItemQuantity> in List<ItemQuantityApi>.
	 */
	private List<ItemQuantityApi> convertToApi(List<ItemQuantity> entity){
		List<ItemQuantityApi> api = new ArrayList<>();
		for(ItemQuantity item : entity) {
			ItemQuantityApi itemApi = new ItemQuantityApi();
			itemApi.setItem(itemRn.convertToApi(item.getItem()));
			itemApi.setQuantity(item.getQuantity());
			api.add(itemApi);
		}
		return api;
	}
	
	/**
	 * Process a buy request and shows the buy confirmation. 
	 * (*needs registration*)
	 */
	public OrderApi buyConfirm() {
		if(!userIsLoggedIn()) {
			return null;
		}
		if(ShoppingSession.getInstance().getCart().isEmpty()) {
			return null;
		}
		Order order = new Order();
		order.setCustomer(customerDao.searchById(ShoppingSession.getInstance().getCustomerId()));
		order.setDate(new Date());
		order.setTotal(calculateTotalPrice(ShoppingSession.getInstance().getCart()));
		order.setStatus(EnumOrderStatus.FINISHED);
		orderDao.persistOrder(order);
		for(ItemQuantity item : ShoppingSession.getInstance().getCart()) {
			if(itemDao.searchById(item.getItem().getId()).getAvailability() <= 0) {
				return null;
			}
			OrderLine line = new OrderLine();
			line.setQuantity(item.getQuantity());
			line.setOrder(order);
			line.setItem(item.getItem());
			orderDao.persistOrderLine(line);
		}
		decreaseAvailability(ShoppingSession.getInstance().getCart());
		ShoppingSession.getInstance().getCart().clear();
		return convertToApi(order);
	}
	
	/**
	 * Decreases items availability.
	 */
	private void decreaseAvailability(List<ItemQuantity> list) {
		for(ItemQuantity iq : list) {
			Item item = itemDao.searchById(iq.getItem().getId());
			item.setAvailability(item.getAvailability() - iq.getQuantity());
			if(item.getAvailability() < 0) {
				item.setAvailability(0);
			}
			itemDao.persistItem(item);
		}
	}
	
	/**
	 * Displays the order details.
	 */
	public OrderApi displayOrder(Long orderId) {
		Order order = orderDao.searchById(orderId);
		return convertToApi(order);
	}
	
	/**
	 * Converts Order to OrderApi.
	 */
	private OrderApi convertToApi(Order order) {
		OrderApi api = new OrderApi();
		api.setId(order.getId());
		api.setDate(order.getDate());
		api.setTotalPrice(order.getTotal());
		List<OrderLine> orderLines = orderDao.searchOrderLineByOrder(order.getId());
		List<ItemQuantityApi> items = new ArrayList<>();
		for(OrderLine line : orderLines) {
			ItemQuantityApi itemApi = new ItemQuantityApi();
			itemApi.setItem(itemRn.convertToApi(line.getItem()));
			itemApi.setQuantity(line.getQuantity());
			items.add(itemApi);
		}
		api.setItems(items);
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
