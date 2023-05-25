package ch.web.web_shop.service;

import ch.web.web_shop.exception.ProductCouldNotBeSavedException;
import ch.web.web_shop.exception.ProductDeleteException;
import ch.web.web_shop.exception.ProductLoadException;
import ch.web.web_shop.exception.ProductNotFoundException;
import ch.web.web_shop.model.Product;
import ch.web.web_shop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts_NoTitle() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts(null);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllProducts_WithTitle() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        when(productRepository.findByTitleContaining("test")).thenReturn(productList);

        List<Product> result = productService.getAllProducts("test");
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllProducts_Exception() {
        when(productRepository.findAll()).thenThrow(RuntimeException.class);

        assertThrows(ProductLoadException.class, () -> {
            productService.getAllProducts(null);
        });
    }

    @Test
    public void testGetProductById_Success() {
        long productId = 1;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);
        assertEquals(product, result);
    }

    @Test
    public void testGetProductById_NotFound() {
        long productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    public void testCreateProduct_Success() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);
        assertEquals(product, result);
    }

    @Test
    public void testCreateProduct_Exception() {
        Product product = new Product();
        when(productRepository.save(product)).thenThrow(RuntimeException.class);

        assertThrows(ProductCouldNotBeSavedException.class, () -> {
            productService.createProduct(product);
        });
    }

    @Test
    public void testUpdateProduct_Success() {
        long productId = 1;
        Product existingProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product updatedProduct = new Product();
        Product result = productService.updateProduct(productId, updatedProduct);

        assertEquals(existingProduct, result);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void testUpdateProduct_NotFound() {
        long productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(productId, new Product());
        });
    }

    @Test
    public void testDeleteProduct_Success() {
        long productId = 1;
        doNothing().when(productRepository).deleteById(productId);

        assertDoesNotThrow(() -> {
            productService.deleteProduct(productId);
        });

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testDeleteProduct_NotFound() {
        long productId = 1;
        doThrow(RuntimeException.class).when(productRepository).deleteById(productId);

        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });
    }

    @Test
    public void testDeleteAllProducts_Success() {
        doNothing().when(productRepository).deleteAll();

        assertDoesNotThrow(() -> {
            productService.deleteAllProducts();
        });

        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteAllProducts_Exception() {
        doThrow(RuntimeException.class).when(productRepository).deleteAll();

        assertThrows(ProductDeleteException.class, () -> {
            productService.deleteAllProducts();
        });
    }

    @Test
    public void testGetPublishedProducts_Success() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        when(productRepository.findByPublished(true)).thenReturn(productList);

        List<Product> result = productService.getPublishedProducts();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPublishedProducts_Exception() {
        when(productRepository.findByPublished(true)).thenThrow(RuntimeException.class);

        assertThrows(ProductLoadException.class, () -> {
            productService.getPublishedProducts();
        });
    }

}
