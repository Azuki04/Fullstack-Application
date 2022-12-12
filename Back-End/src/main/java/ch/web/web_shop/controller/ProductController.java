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
 * Product page controller class.
 * This controller and related pages can be accessed by all users, regardless of
 * their roles. However, I currently have no login yet
 * This class is a mainClass and must be loaded when the application starts.
 * The @RequestMapping("/api/products") annotation informs that this controller
 * will process requests whose URI begins with "/api/products".
 *
 * @author Sy Viet
 * @version 3.0
 * @see Products
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")

public class ProductController {

	@Autowired
	ProductRepository productRepository;

	/**
	 * @author Sy Viet Dao
	 *         get all product from web_shop.
	 *         URL request {"/api/products"}, method get.
	 *
	 * @param title to find the product with a title.
	 * @return ResponseEntity: products.
	 */
	@GetMapping("")
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String title) {
		List<Product> products = new ArrayList<Product>();
		try {

			if (title == null) {
				productRepository.findAll().forEach(products::add);
			} else {
				productRepository.findByTitleContaining(title).forEach(products::add);
			}

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception ex) {
			throw new ProductLoadException();
		}
		
		return ResponseEntity.ok(products);
	}

	/**
	 * @author Sy Viet Dao
	 *         get productDetail with specific id from web_shop.
	 *         URL request {"/api/products/{id}, method get.
	 *
	 * @param id from the product to show the details.
	 * @return ResponseEntity: productDetail.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			return ResponseEntity.ok(productData.get());
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @author Sy Viet Dao
	 *         create product from web_shop.
	 *         URL request {"/api/products"}, method POST.
	 *
	 * @param product the product is created and stored in MySQL.
	 * @return ResponseEntity: product.
	 */
	@PostMapping("")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		try {
			Product productCreate = productRepository
					.save(new Product(product.getTitle(), product.getDescription(), product.getContent(),
							product.getPrice(), product.getStock(), product.getSrc(), false, product.getCategory()));
			return ResponseEntity.ok(productCreate);
		} catch (Exception ex) {
			throw new ProductCouldNotBeSavedException(product.getTitle());
		}
	}

	/**
	 * Update product from web_shop.
	 * URL request {"/api/products/{id}"}, method PUT.
	 *
	 * @param product the product to be removed from the user shopping cart.
	 */

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			Product _product = productData.get();
			_product.setTitle(product.getTitle());
			_product.setDescription(product.getDescription());
			_product.setPrice(product.getPrice());
			_product.setStock(product.getStock());
			_product.setContent(product.getContent());
			_product.setPublished(product.isPublished());
			return ResponseEntity.ok(productRepository.save(_product));
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Remove one product from web_shop.
	 * URL request {"/api/products/{id}"}, method DELETE.
	 *
	 * @param id the product to be removed from the user shopping cart.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			productRepository.deleteById(id);
			return ResponseEntity.ok(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Remove ALL products from web_shop.
	 * URL request {"/api/products"}, method DELETE.
	 * deleteAll() all products delete
	 * 
	 * @param id the product to be removed from the user shopping cart.
	 */
	@DeleteMapping("")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			productRepository.deleteAll();
			return ResponseEntity.ok(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/published")
	public ResponseEntity<List<Product>> findByPublished() {
		try {
			List<Product> products = productRepository.findByPublished(true);

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
