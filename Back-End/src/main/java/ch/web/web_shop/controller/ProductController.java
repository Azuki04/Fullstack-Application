package ch.web.web_shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.web.web_shop.exception.ProductCouldNotBeSavedException;
import ch.web.web_shop.exception.ProductLoadException;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.repository.ProductRepository;

/**
 * Product controller class.
 * This controller manages product-related operations.
 * The @RestController annotation combines the @Controller and @ResponseBody annotations,
 * simplifying the code and eliminating the need for individual @ResponseBody annotations.
 * The @RequestMapping("/api/products") annotation informs that this controller
 * will process requests whose URI begins with "/api/products".
 * Handles HTTP methods such as GET, POST, etc. using appropriate mapping annotations.
 * Manages and provides access to Product objects.
 *
 * @author Sy Viet
 * @version 3.0
 * @see Product
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	/**
	 * Retrieves all products.
	 *
	 * @param title The title parameter to filter products by title (optional).
	 * @return ResponseEntity with a list of products if successful,
	 *         or an error response if an exception occurs.
	 */
	@GetMapping("")
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String title) {
		try {
			List<Product> products = new ArrayList<>();

			if (title == null) {
				productRepository.findAll().forEach(products::add);
			} else {
				productRepository.findByTitleContaining(title).forEach(products::add);
			}

			if (products.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(products);
		} catch (Exception ex) {
			throw new ProductLoadException();
		}
	}

	/**
	 * Retrieves a specific product by its ID.
	 *
	 * @param id The ID of the product to retrieve.
	 * @return ResponseEntity with the product if found,
	 *         or a not found response if the product does not exist.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			return ResponseEntity.ok(productData.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Creates a new product.
	 *
	 * @param product The product to create.
	 * @return ResponseEntity with the created product if successful,
	 *         or an error response if the product could not be saved.
	 */
	@PostMapping("")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		try {
			Product createdProduct = productRepository.save(product);
			return ResponseEntity.ok(createdProduct);
		} catch (Exception ex) {
			throw new ProductCouldNotBeSavedException(product.getTitle());
		}
	}

	/**
	 * Updates a specific product.
	 *
	 * @param id      The ID of the product to update.
	 * @param product The updated product data.
	 * @return ResponseEntity with the updated product if successful,
	 *         or a not found response if the product does not exist.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			Product existingProduct = productData.get();
			existingProduct.setTitle(product.getTitle());
			existingProduct.setDescription(product.getDescription());
			existingProduct.setPrice(product.getPrice());
			existingProduct.setStock(product.getStock());
			existingProduct.setContent(product.getContent());
			existingProduct.setPublished(product.isPublished());

			Product updatedProduct = productRepository.save(existingProduct);
			return ResponseEntity.ok(updatedProduct);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Deletes a specific product by its ID.
	 *
	 * @param id The ID of the product to delete.
	 * @return ResponseEntity with a success status if the product was deleted,
	 *         or an error response if an exception occurs.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			productRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	/**
	 * Deletes all products.
	 *
	 * @return ResponseEntity with a success status if all products were deleted,
	 *         or an error response if an exception occurs.
	 */
	@DeleteMapping("")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			productRepository.deleteAll();
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	/**
	 * Retrieves all published products.
	 *
	 * @return ResponseEntity with a list of published products if successful,
	 *         or an error response if an exception occurs.
	 */
	@GetMapping("/published")
	public ResponseEntity<List<Product>> findByPublished() {
		try {
			List<Product> publishedProducts = productRepository.findByPublished(true);

			if (publishedProducts.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(publishedProducts);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
