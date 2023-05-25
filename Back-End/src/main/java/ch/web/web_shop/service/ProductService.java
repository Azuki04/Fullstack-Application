package ch.web.web_shop.service;

import java.util.List;
import java.util.Optional;

import ch.web.web_shop.exception.ProductDeleteException;
import ch.web.web_shop.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.web.web_shop.exception.ProductCouldNotBeSavedException;
import ch.web.web_shop.exception.ProductLoadException;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.repository.ProductRepository;

/**
 * Product service class.
 * Handles the business logic for product operations.
 * Uses ProductRepository for data persistence.
 * This class can be used to perform additional business logic if needed.
 * It provides a layer of abstraction between the controller and the repository.
 * The @Service annotation is used to indicate that this class is a service component.
 * It enables component scanning and automatic dependency injection.
 *
 * @author Sy Viet
 * @version 1.0
 * @see Product
 * @see ProductRepository
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products.
     *
     * @param title The title parameter to filter products by title (optional).
     * @return A list of products if successful, or an exception if an error occurs.
     */
    public List<Product> getAllProducts(String title) {
        try {
            if (title == null) {
                return (List<Product>) productRepository.findAll();
            } else {
                return productRepository.findByTitleContaining(title);
            }
        } catch (Exception ex) {
            throw new ProductLoadException();
        }
    }

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The product if found, or an exception if the product does not exist.
     */
    public Product getProductById(long id) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            return productData.get();
        } else {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    /**
     * Creates a new product.
     *
     * @param product The product to create.
     * @return The created product if successful, or an exception if the product could not be saved.
     */
    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception ex) {
            throw new ProductCouldNotBeSavedException(product.getTitle());
        }
    }

    /**
     * Updates a specific product.
     *
     * @param id      The ID of the product to update.
     * @param product The updated product data.
     * @return The updated product if successful, or an exception if the product does not exist.
     */
    public Product updateProduct(long id, Product product) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product existingProduct = productData.get();
            existingProduct.setTitle(product.getTitle());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            existingProduct.setContent(product.getContent());
            existingProduct.setPublished(product.isPublished());

            return productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    /**
     * Deletes a specific product by its ID.
     *
     * @param id The ID of the product to delete.
     */
    public void deleteProduct(long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(String.valueOf(id));
        }
    }

    /**
     * Deletes all products.
     */
    public void deleteAllProducts() {
        try {
            productRepository.deleteAll();
        } catch (Exception e) {
            throw new ProductDeleteException();
        }
    }

    /**
     * Retrieves all published products.
     *
     * @return A list of published products if successful, or an exception if an error occurs.
     */
    public List<Product> getPublishedProducts() {
        try {
            return productRepository.findByPublished(true);
        } catch (Exception e) {
            throw new ProductLoadException();
        }
    }
}
