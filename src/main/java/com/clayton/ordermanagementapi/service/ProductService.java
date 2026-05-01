package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateProductRequest;
import com.clayton.ordermanagementapi.dto.ProductResponse;
import com.clayton.ordermanagementapi.entity.Product;
import com.clayton.ordermanagementapi.exception.ResourceNotFoundException;
import com.clayton.ordermanagementapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductResponse create(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setAvailable(true);

        Product saved = repository.save(product);

        return new ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getAvailable()
        );
    }
    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getAvailable()
                ))
                .toList();
    }

    public Product update(Long id, Product data) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setAvailable(data.getAvailable());

        return repository.save(product);
    }

    public void delete(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setAvailable(false);

        repository.save(product);
    }

    public ProductResponse getProductById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailable()
        );
    }
}
