package dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Item;
import util.EnumSearchType;

@Stateful
public class ItemDao {
	
	@PersistenceContext
	EntityManager em;
	
	public Item searchById(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select i from Item i ");
		sb.append("where i.id = :id ");
		TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	public List<Item> search(EnumSearchType searchType, String searchText){
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select i from Item i ");
			switch(searchType) {
			case AUTHOR:
				sb.append("where i.author.fullName = :searchText ");
				break;
			case TITLE:
				sb.append("where i.title = :searchText ");
				break;
			case SUBJECT:
				sb.append("where i.subject = :searchText ");
				break;
			}
			TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
			query.setParameter("searchText", searchText);
			query.setMaxResults(50);
			return query.getResultList();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public List<Item> bestSellers(String subject){		
		StringBuilder sb = new StringBuilder();
		sb.append("select i from Item i ");
		sb.append("where i.subject = :subject ");
		sb.append("order by i.title asc ");
		TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
		query.setParameter("subject", subject);
		query.setMaxResults(50);
		return query.getResultList();
	}
	
	public List<Item> newProducts(String subject){		
		StringBuilder sb = new StringBuilder();
		sb.append("select i from Item i ");
		sb.append("where i.subject = :subject ");
		sb.append("order by i.publicationDate desc, ");
		sb.append("i.title asc ");
		TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
		query.setParameter("subject", subject);
		query.setMaxResults(50);
		return query.getResultList();
	}
	
	public List<String> getSubjects() {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct subject from Item i ");
		TypedQuery<String> query = em.createQuery(sb.toString(), String.class);
		return query.getResultList();
	}
	
	public Item productDetail(Long id) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select i from Item i ");
			sb.append("where i.id = :id ");
			TypedQuery<Item> query = em.createQuery(sb.toString(), Item.class);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public void persistItem(Item item) {
		em.merge(item);
	}
}
