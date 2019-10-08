package dao;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Customer;

@Stateful
public class CustomerDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public Customer searchById(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select c from Customer c ");
		sb.append("where c.id = :id ");
		TypedQuery<Customer> query = em.createQuery(sb.toString(), Customer.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	public Customer fetchCustomer(String username) {
		StringBuilder sb = new StringBuilder();
		sb.append("select c from Customer c ");
		sb.append("where c.username = :username ");
		TypedQuery<Customer> query = em.createQuery(sb.toString(), Customer.class);
		query.setParameter("username", username);
		return query.getSingleResult();
	}
	
	public void persistCustomer(Customer customer) {
		em.persist(customer);
	}
}
