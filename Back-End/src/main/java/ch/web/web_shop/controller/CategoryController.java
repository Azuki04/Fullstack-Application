package ch.web.web_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.repository.CategoryRepository;

/**
 * Category controller class.
 * This class is used to manage categories.
 * The @RestController annotation combines the @Controller and @ResponseBody annotations,
 * simplifying the code and eliminating the need for individual @ResponseBody annotations.
 * The @RequestMapping("/api/category") annotation informs that this controller
 * will process requests whose URI begins with "/api/category".
 * Handles HTTP methods such as GET, POST, etc. using appropriate mapping annotations.
 * Manages and provides access to Category objects.
 * Uses CategoryRepository to retrieve and manipulate data.
 *
 * @author Sy Viet
 * @version 1.0
 * @see Category
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * Retrieves all categories.
	 *
	 * @return ResponseEntity with a list of categories if successful,
	 *         or an error response if an exception occurs.
	 */
	@GetMapping("")
	public ResponseEntity<Iterable<Category>> getAllCategories() {
		try {
			Iterable<Category> categories = categoryRepository.findAll();
			return ResponseEntity.ok(categories);
		} catch (DataAccessException ex) {
			// Handle specific data access exception, such as DataAccessException, DataAccessResourceFailureException, etc.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (Exception ex) {
			// Handle other generic exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}