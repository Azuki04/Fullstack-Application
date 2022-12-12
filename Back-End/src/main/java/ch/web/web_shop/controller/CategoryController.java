package ch.web.web_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import ch.web.web_shop.exception.CategoryLoadException;
import ch.web.web_shop.model.Category;
import ch.web.web_shop.repository.CategoryRepository;

/**
 * Category controller class.
 * This class is used to manage categories
 * The @RequestMapping("/api/category") annotation informs that this controller
 * will process requests whose URI begins with "/api/category".
 *
 * @author Sy Viet
 * @version 1.0
 * @see category
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/category")

public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;

	// gett all categories
	@GetMapping(path = "")
	public ResponseEntity<Iterable<Category>> getAllCategories() {
		Iterable<Category> categories = null;

		try {
			categories = categoryRepository.findAll();
		} catch (Exception ex) {
			throw new CategoryLoadException();
		}

		return ResponseEntity.ok(categories);
	}
}
