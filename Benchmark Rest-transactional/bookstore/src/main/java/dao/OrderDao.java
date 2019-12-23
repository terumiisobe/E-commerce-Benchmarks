package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Order;
import model.OrderLine;
import util.ShoppingSession;

@Stateful
public class OrderDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public Order searchById(Long orderId) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select o from Order o ");
			sb.append("where o.id = :orderId ");
			TypedQuery<Order> query = em.createQuery(sb.toString(), Order.class);
			query.setParameter("orderId", orderId);
			return query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public List<OrderLine> searchOrderLineByOrder(Long orderId){
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select ol from OrderLine ol ");
			sb.append("where ol.order.id = :orderId ");
			TypedQuery<OrderLine> query = em.createQuery(sb.toString(), OrderLine.class);
			query.setParameter("orderId", orderId);
			return query.getResultList();
		} catch(NoResultException e) {
			return new ArrayList<>();
		}
	}
	
	public ShoppingSession searchShoppingSessionByCustomer(Long customerId) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select ss from ShoppingSession ss ");
			sb.append("where ss.customerId = :customerId ");
			TypedQuery<ShoppingSession> query = em.createQuery(sb.toString(), ShoppingSession.class);
			query.setParameter("customerId", customerId);
			return query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public Order getLastOrder(Long customerId) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select o from Order o ");
			sb.append("where o.customer.id = :customerId ");
			sb.append("order by o.date desc ");
			TypedQuery<Order> query = em.createQuery(sb.toString(), Order.class);
			query.setParameter("customerId", customerId);
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void persistOrder(Order order) {
		em.persist(order);
	}
	
	public void persistOrderLine(OrderLine orderLine) {
		em.persist(orderLine);
	}
	
	public void persistShoppingSession(ShoppingSession shoppingSession) {
		em.persist(shoppingSession);
	}
	
	public void updateShoppingSession(ShoppingSession shoppingSession) {
		em.merge(shoppingSession);
	}

}
