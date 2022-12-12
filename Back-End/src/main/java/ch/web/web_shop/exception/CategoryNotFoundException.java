
package ch.web.web_shop.exception;

public class CategoryNotFoundException extends RuntimeException {
	public CategoryNotFoundException() {
		super("The category with id  could not be found.");
	}
}
