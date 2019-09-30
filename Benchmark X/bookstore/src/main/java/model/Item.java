package model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
	
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@ManyToOne
	@JoinColumn(name = "authorId")
	private Author author;
	private String subject;
	private String publisher;
	private Date publicationDate;
	private BigDecimal cost;
	private Long availability;
	private Long timesSold;
	
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
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Date getPublishDate() {
		return publicationDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publicationDate = publishDate;
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
	public Long getTimesSold() {
		return timesSold;
	}
	public void setTimesSold(Long timesSold) {
		this.timesSold = timesSold;
	}
}
