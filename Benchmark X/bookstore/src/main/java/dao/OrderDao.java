package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Order;
import model.OrderLine;
import util.EnumOrderStatus;

public class OrderDao {
	
	private EntityManager em;
	
	
	public Order searchById(Long orderId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o from Order ");
		sb.append("where o.id = :orderId ");
		TypedQuery<Order> query = em.createQuery(sb.toString(), Order.class);
		query.setParameter("orderId", orderId);
		return query.getSingleResult();
	}
	
	public Order fetchOrder(Long customerId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o from Order ");
		sb.append("where o.customerId = :customerId ");
		sb.append("and o.status != :canceled ");
		TypedQuery<Order> query = em.createQuery(sb.toString(), Order.class);
		query.setParameter("customerId", customerId);
		query.setParameter("canceled", EnumOrderStatus.CANCELED);
		return query.getSingleResult();
	}
	
	public List<OrderLine> fetchOrderLine(Long orderId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ol from OrderLine ");
		sb.append("where ol.orderId = :orderId ");
		TypedQuery<OrderLine> query = em.createQuery(sb.toString(), OrderLine.class);
		query.setParameter("orderId", orderId);
		return query.getResultList();
	}
	
	public void persistOrder(Order order) {
		em.persist(order);
	}
	
	public void persistOrderLine(OrderLine orderLine) {
		em.persist(orderLine);
	}

}
