package dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Order;
import model.OrderLine;
import util.EnumOrderStatus;

@Stateful
public class OrderDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public Order searchById(Long orderId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o from Order o ");
		sb.append("where o.id = :orderId ");
		TypedQuery<Order> query = em.createQuery(sb.toString(), Order.class);
		query.setParameter("orderId", orderId);
		return query.getSingleResult();
	}
	
	public List<OrderLine> searchOrderLineByOrder(Long orderId){
		StringBuilder sb = new StringBuilder();
		sb.append("select ol from OrderLine ol ");
		sb.append("where ol.order.id = :orderId ");
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
