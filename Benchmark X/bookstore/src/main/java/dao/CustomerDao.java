package dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Customer;
import model.Order;

public class CustomerDao {
	
	private EntityManager em;
	
	public Customer searchById(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select c from Customer ");
		sb.append("where c.id = :id ");
		TypedQuery<Customer> query = em.createQuery(sb.toString(), Customer.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	public Customer fetchCustomer(String username) {
		StringBuilder sb = new StringBuilder();
		sb.append("select c from Customer ");
		sb.append("where c.username = :username ");
		TypedQuery<Customer> query = em.createQuery(sb.toString(), Customer.class);
		query.setParameter("username", username);
		return query.getSingleResult();
	}
	
	public void persistCustomer(Customer customer) {
		em.persist(customer);
	}
}
