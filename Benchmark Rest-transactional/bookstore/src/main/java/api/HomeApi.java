package api;

import java.util.List;

public class HomeApi {
	private String customerName;
	private List<String> whatsNew;
	private List<String> bestSellers;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<String> getWhatsNew() {
		return whatsNew;
	}
	public void setWhatsNew(List<String> whatsNew) {
		this.whatsNew = whatsNew;
	}
	public List<String> getBestSellers() {
		return bestSellers;
	}
	public void setBestSellers(List<String> bestSellers) {
		this.bestSellers = bestSellers;
	}
}
