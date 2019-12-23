package exception;

public enum BookstoreExceptionMessages {
	BOOK_1("Customer not registered."),
	BOOK_2("Customer doesn't exist."),
	BOOK_3("Wrong password."),
	BOOK_4("Book not available."),
	BOOK_5("Shopping cart is empty.");
	
	private String message;
	
	BookstoreExceptionMessages(String message) {
		this.message = message;
	} 
	
	public String getMessage() {
		return message;
	}
}
