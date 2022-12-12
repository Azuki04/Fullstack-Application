
package ch.web.web_shop.exception;

public class CategoryLoadException extends RuntimeException {
	public CategoryLoadException() {
		super("Categories could not be loaded.");
	}
}
