package ch.web.web_shop.controller;
import ch.web.web_shop.controller.ProductController;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(productService.getAllProducts(null)).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getAllProducts(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productService, times(1)).getAllProducts(null);
    }

    @Test
    void testGetProductById() {
        long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        Product createdProduct = new Product();
        when(productService.createProduct(product)).thenReturn(createdProduct);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
        verify(productService, times(1)).createProduct(product);
    }

    @Test
    void testUpdateProduct() {
        long productId = 1L;
        Product product = new Product();
        Product updatedProduct = new Product();
        when(productService.updateProduct(productId, product)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(productId, product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
        verify(productService, times(1)).updateProduct(productId, product);
    }

    @Test
    void testDeleteProduct() {
        long productId = 1L;
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.noContent().build();

        ResponseEntity<HttpStatus> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(expectedResponse, response);
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void testDeleteAllProducts() {
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.noContent().build();

        ResponseEntity<HttpStatus> response = productController.deleteAllProducts();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(expectedResponse, response);
        verify(productService, times(1)).deleteAllProducts();
    }

    @Test
    void testFindByPublished() {
        List<Product> publishedProducts = new ArrayList<>();
        publishedProducts.add(new Product());
        when(productService.getPublishedProducts()).thenReturn(publishedProducts);

        ResponseEntity<List<Product>> response = productController.findByPublished();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(publishedProducts, response.getBody());
        verify(productService, times(1)).getPublishedProducts();
    }

}