package ch.web.web_shop.model;

import ch.web.web_shop.controller.ProductController;
import ch.web.web_shop.model.Category;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // ...

    @Test
    void testCreateProduct1() {
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10);
        product.setStock(100);
        product.setPublished(true);
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        product.setCategory(category);

        Product createdProduct = new Product();
        createdProduct.setId(1L);
        createdProduct.setTitle("Test Product");
        createdProduct.setDescription("Test Description");
        createdProduct.setPrice(10);
        createdProduct.setStock(100);
        createdProduct.setPublished(true);
        createdProduct.setCategory(category);

        when(productService.createProduct(product)).thenReturn(createdProduct);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
        verify(productService, times(1)).createProduct(product);
    }
    @Test
    public void testCreateProduct2() {
        String title = "Test Product";
        String description = "This is a test product";
        String content = "Test product content";
        int price = 10;
        int stock = 100;
        String src = "test.jpg";
        boolean published = true;

        Category category = new Category("Test Category");

        Product product = new Product(title, description, content, price, stock, src, published, category);

        Assertions.assertEquals(title, product.getTitle());
        Assertions.assertEquals(description, product.getDescription());
        Assertions.assertEquals(content, product.getContent());
        Assertions.assertEquals(price, product.getPrice());
        Assertions.assertEquals(stock, product.getStock());
        Assertions.assertEquals(src, product.getSrc());
        Assertions.assertEquals(published, product.isPublished());
        Assertions.assertEquals(category, product.getCategory());
    }
    @Test
    public void testGettersAndSetters() {
        // Prepare test data
        long id = 1L;
        String title = "Test Product";
        String description = "This is a test product";
        String content = "Lorem ipsum dolor sit amet";
        int price = 10;
        int stock = 100;
        String src = "image.jpg";
        boolean published = true;

        // Create a product instance
        Product product = new Product();

        // Set the properties using the setter methods
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setContent(content);
        product.setPrice(price);
        product.setStock(stock);
        product.setSrc(src);
        product.setPublished(published);

        // Verify the values using the getter methods
        Assertions.assertEquals(id, product.getId());
        Assertions.assertEquals(title, product.getTitle());
        Assertions.assertEquals(description, product.getDescription());
        Assertions.assertEquals(content, product.getContent());
        Assertions.assertEquals(price, product.getPrice());
        Assertions.assertEquals(stock, product.getStock());
        Assertions.assertEquals(src, product.getSrc());
        Assertions.assertEquals(published, product.isPublished());
    }
}
