package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Author;
import model.Customer;
import model.Item;

@Stateful
public class ItemDao {
	
	private EntityManager em;
	
	public Item searchById(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select i from Item ");
		sb.append("where i.id = :id ");
		TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	public List<Item> list(String title, String author, Boolean sorted, Long maxPrice){		
		StringBuilder sb = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		sb.append("select i from Item i ");
		sb.append("where 1=1 ");
		if(title != null) {
			sb.append("and i.title like :title ");
			parameters.put("title", title);
		}
		if(author != null) {
			sb.append("and i.author like :author ");
			parameters.put("author", author);
		}
		if(maxPrice != null) {
			sb.append("and i.cost <= :maxPrice ");
			parameters.put("maxPrice", maxPrice);
		}
		if(sorted != null) {
			sb.append("order by i.cost ");
		}
		TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
		return query.getResultList();
	}
	
	public List<Item> listBestSellers(){		
		StringBuilder sb = new StringBuilder();
		sb.append("select i from Item i ");
		sb.append("order by timesSold desc ");
		TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
		query.setMaxResults(10);
		return query.getResultList();
	}
	
	public List<Item> listRelatedBooks(Long customerId){		
		StringBuilder sb = new StringBuilder();
		sb.append("select a from OrderLine ol ");
		sb.append("join ol.order o ");
		sb.append("join o.customer c ");
		sb.append("join ol.item i ");
		sb.append("join i.author a ");
		sb.append("where c.id = :customerId ");
		TypedQuery<Author> authorQuery = em.createQuery(sb.toString(), Author.class);
		authorQuery.setParameter("customerId", customerId);
		List<Long> authors = authorQuery.getResultList().stream().map(a -> a.getId()).collect(Collectors.toList());
		
		sb = new StringBuilder();
		sb.append("select i from Item ");
		sb.append("join i.author a ");
		sb.append("where a.id in :authors ");
		TypedQuery<Item> itemQuery = em.createQuery(sb.toString(), Item.class);
		itemQuery.setParameter("authors", authors);
		itemQuery.setMaxResults(10);
		return itemQuery.getResultList();
	}
	
	public Item productDetail(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select i from Item i ");
		sb.append("where i.id = :id ");
		TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
}
