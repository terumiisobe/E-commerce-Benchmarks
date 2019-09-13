package dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Order;
import util.EnumOrderStatus;

public class OrderDao {
	
	private EntityManager em;

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

}
