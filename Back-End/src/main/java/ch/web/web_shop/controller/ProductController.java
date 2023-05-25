package ch.web.web_shop.controller;

import java.util.List;

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

import ch.web.web_shop.model.Product;
import ch.web.web_shop.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("")
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String title) {
		List<Product> products = productService.getAllProducts(title);

		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}

	@PostMapping("")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		return ResponseEntity.ok(createdProduct);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		Product updatedProduct = productService.updateProduct(id, product);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		productService.deleteAllProducts();
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/published")
	public ResponseEntity<List<Product>> findByPublished() {
		List<Product> publishedProducts = productService.getPublishedProducts();

		if (publishedProducts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(publishedProducts);
	}
}
