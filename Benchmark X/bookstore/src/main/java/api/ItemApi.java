package api;

import java.math.BigDecimal;

public class ItemApi {
	
	private Long id;
	private String title;
	private String author;
	private String publisher;
	private BigDecimal cost;
	private Long availability;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public Long getAvailability() {
		return availability;
	}
	public void setAvailability(Long availability) {
		this.availability = availability;
	}
}
