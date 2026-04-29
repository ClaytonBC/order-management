package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateProductRequest;
import com.clayton.ordermanagementapi.dto.ProductResponse;
import com.clayton.ordermanagementapi.entity.Product;
import com.clayton.ordermanagementapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldCreateProductSuccessfully() {

        CreateProductRequest request =
                new CreateProductRequest(
                        "Mouse",
                        "Mouse Gamer",
                        BigDecimal.valueOf(150)
                );

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Mouse");
        saved.setDescription("Mouse Gamer");
        saved.setPrice(BigDecimal.valueOf(150));
        saved.setAvailable(true);

        when(repository.save(any(Product.class)))
                .thenReturn(saved);

        ProductResponse response = service.create(request);

        assertEquals("Mouse", response.name());
        assertEquals(true, response.available());

        verify(repository).save(any(Product.class));
    }

    @Test
    void shouldReturnAllProducts() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Mouse");
        product1.setDescription("Mouse Gamer");
        product1.setPrice(BigDecimal.valueOf(150));
        product1.setAvailable(true);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Teclado");
        product2.setDescription("Teclado Mecânico");
        product2.setPrice(BigDecimal.valueOf(300));
        product2.setAvailable(true);

        when(repository.findAll())
                .thenReturn(List.of(product1, product2));

        List<ProductResponse> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("Mouse", result.get(0).name());
        assertEquals("Teclado", result.get(1).name());

        verify(repository).findAll();
    }

    @Test
    void shouldUpdateProductSuccessfully() {

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Mouse");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(BigDecimal.valueOf(100));
        existingProduct.setAvailable(true);

        Product updateData = new Product();
        updateData.setName("New Mouse");
        updateData.setDescription("New Description");
        updateData.setPrice(BigDecimal.valueOf(150));
        updateData.setAvailable(false);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existingProduct));

        when(repository.save(any(Product.class)))
                .thenReturn(existingProduct);

        Product result = service.update(1L, updateData);

        assertEquals("New Mouse", result.getName());
        assertEquals("New Description", result.getDescription());
        assertEquals(BigDecimal.valueOf(150), result.getPrice());
        assertFalse(result.getAvailable());

        verify(repository).findById(1L);
        verify(repository).save(existingProduct);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {

        Product updateData = new Product();
        updateData.setName("Mouse");

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.update(1L, updateData)
        );

        assertEquals("Product not found", exception.getMessage());

        verify(repository).findById(1L);
    }

    @Test
    void shouldSoftDeleteProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Mouse");
        product.setAvailable(true);

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        when(repository.save(any(Product.class)))
                .thenReturn(product);

        service.delete(1L);

        assertFalse(product.getAvailable());

        verify(repository).findById(1L);
        verify(repository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.delete(1L)
        );

        assertEquals("Product not found", exception.getMessage());

        verify(repository).findById(1L);
    }
}
