package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Item;

@Stateful
public class ItemDao {
	
	private EntityManager em;

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
}
