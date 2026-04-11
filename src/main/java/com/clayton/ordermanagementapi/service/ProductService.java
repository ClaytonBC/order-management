package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateProductRequest;
import com.clayton.ordermanagementapi.dto.ProductResponse;
import com.clayton.ordermanagementapi.entity.Product;
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
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
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
}
