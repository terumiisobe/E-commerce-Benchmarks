package rn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UpdateModelException;
import javax.inject.Inject;

import api.ItemQuantityApi;
import api.OrderApi;
import api.RegistrationApi;
import api.ShoppingCartApi;
import dao.CustomerDao;
import dao.ItemDao;
import dao.OrderDao;
import exception.BookstoreException;
import exception.BookstoreExceptionMessages;
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
	
	/**
	 * Register an user by performing log in. Returns customer's token.
	 */
	public Long customerRegistration(Boolean returningCustomer, String username, String password, 
			RegistrationApi registration) throws BookstoreException{
		Customer customer;
		if(returningCustomer) {
			customer = customerDao.fetchCustomer(username);
			if(customer == null) {
				throw new BookstoreException(BookstoreExceptionMessages.BOOK_2.getMessage());
			}else if(!customer.getPassword().equals(password)){
				throw new BookstoreException(BookstoreExceptionMessages.BOOK_3.getMessage());
			}
		}
		else{
			customer = new Customer();
			customer.setUsername(Long.toString(System.currentTimeMillis()));
			customer.setPassword("password");
			customer.setFullName(registration.getFullName());
			customer.setAddress(registration.getAddress());
			customer.setPhoneNumber(registration.getPhoneNumber());
			customer.setEmail(registration.getEmail());
			customer.setBirthDate(registration.getBirthDate());
			customerDao.persistCustomer(customer);
		}
		ShoppingSession shoppingSession = new ShoppingSession();
		shoppingSession.setCustomerId(customer.getId());
		shoppingSession.setLoginTime(new Date());
		shoppingSession.setTotalPrice(new BigDecimal(0));
		shoppingSession.setCart(new ArrayList<>());
		orderDao.persistShoppingSession(shoppingSession);
		
		return shoppingSession.getCustomerId();
	}
	
	/**
	 * Gets customer's shopping session.
	 */
	public ShoppingSession getCustomerSession(Long token) {
		ShoppingSession session = orderDao.searchShoppingSessionByCustomer(token);
		return session;
	}
	
	/**
	 * Adds product to shopping cart. 
	 * (*needs registration*)
	 */
	public ShoppingCartApi shoppingCart(Long token, Boolean addFlag, HashMap<Long, Integer> items) throws BookstoreException {
		ShoppingSession session = getCustomerSession(token);
		if(session == null) {
			throw new BookstoreException(BookstoreExceptionMessages.BOOK_1.getMessage());
		}
		// if ADD and the cart is not full
		if(addFlag && session.getCart().size() < 100) {
			for(Long itemId : items.keySet()) {
				if(itemDao.searchById(itemId).getAvailability() <= 0) {
					throw new BookstoreException(BookstoreExceptionMessages.BOOK_4.getMessage());
				}
				List<ItemQuantity> list = session.getCart();
				Boolean itemExist = false;
				for(ItemQuantity iq : list) {
					// if item already exists in cart
					if(iq.getItemId() == itemId) {
						iq.setQuantity(items.get(itemId));
						itemExist = true;
					}
				}
				//if item doesn't exist in cart
				if(!itemExist) {
					ItemQuantity newItem = new ItemQuantity();
					newItem.setItemId(itemDao.searchById(itemId).getId());
					newItem.setQuantity(items.get(itemId));
					session.getCart().add(newItem);
				}
			}
			session.setTotalPrice(calculateTotalPrice(session.getCart()));
			orderDao.updateShoppingSession(session);
		}
		ShoppingCartApi api = new ShoppingCartApi();
		List<ItemQuantity> cart = session.getCart();
		api.setCart(convertToApi(cart));
		api.setTotalPrice(calculateTotalPrice(cart));
		return api;
	}
	
	/**
	 * Process a buy request and shows the buy confirmation. 
	 * (*needs registration*)
	 */
	public OrderApi buyConfirm(Long token) throws BookstoreException {
		ShoppingSession session = orderDao.searchShoppingSessionByCustomer(token);
		if(session == null) {
			throw new BookstoreException(BookstoreExceptionMessages.BOOK_1.getMessage());
		}
		if(session.getCart().isEmpty()) {
			throw new BookstoreException(BookstoreExceptionMessages.BOOK_5.getMessage());
		}
		Order order = new Order();
		order.setCustomer(customerDao.searchById(session.getCustomerId()));
		order.setDate(new Date());
		order.setTotal(session.getTotalPrice());
		order.setStatus(EnumOrderStatus.FINISHED);
		orderDao.persistOrder(order);
		for(ItemQuantity iq : session.getCart()) {
			Item item = itemDao.searchById(iq.getItemId()); 
			if(item.getAvailability() <= 0) {
				return null;
			}
			OrderLine line = new OrderLine();
			line.setQuantity(iq.getQuantity());
			line.setOrder(order);
			line.setItem(item);
			orderDao.persistOrderLine(line);
		}
		decreaseAvailability(session.getCart());
		session.getCart().clear();
		session.setTotalPrice(new BigDecimal(0));
		orderDao.updateShoppingSession(session);
		return convertToApi(order);
	}
	
	/**
	 * Displays the order details.
	 * (*needs registration*)
	 */
	public OrderApi displayOrder(Long token) throws BookstoreException {
		ShoppingSession session = getCustomerSession(token);
		if(session == null) {
			throw new BookstoreException(BookstoreExceptionMessages.BOOK_1.getMessage());
		}
		Order lastOrder = orderDao.getLastOrder(token);
		OrderApi orderApi = new OrderApi();
		if(lastOrder != null) {
			orderApi.setId(lastOrder.getId());
			orderApi.setDate(lastOrder.getDate());
			orderApi.setTotalPrice(lastOrder.getTotal());
			List<OrderLine> lines = orderDao.searchOrderLineByOrder(lastOrder.getId());
			List<ItemQuantityApi> itemQuantityApi = new ArrayList<>();
			for(OrderLine line : lines) {
				ItemQuantityApi iq = new ItemQuantityApi();
				iq.setItem(itemRn.convertToApi(line.getItem()));
				iq.setQuantity(line.getQuantity());
				itemQuantityApi.add(iq);
			}
			orderApi.setItems(itemQuantityApi);
		}
		return orderApi;	
	}
	
	/**
	 * Decreases items availability.
	 */
	private void decreaseAvailability(List<ItemQuantity> list) {
		for(ItemQuantity iq : list) {
			Item item = itemDao.searchById(iq.getItemId());
			item.setAvailability(item.getAvailability() - iq.getQuantity());
			if(item.getAvailability() < 0) {
				item.setAvailability(0);
			}
			itemDao.persistItem(item);
		}
	}
	
	/**
	 * Calculates total price in shopping cart.
	 */
	private BigDecimal calculateTotalPrice(List<ItemQuantity> cart) {
		BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal price = new BigDecimal(0);
		for(ItemQuantity iq : cart) {
			Item item = itemDao.searchById(iq.getItemId());
			price = item.getCost().multiply(new BigDecimal(iq.getQuantity())); 
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
			itemApi.setItem(itemRn.convertToApi(itemDao.searchById(item.getItemId())));
			itemApi.setQuantity(item.getQuantity());
			api.add(itemApi);
		}
		return api;
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
