package com.clayton.ordermanagementapi.repository;

import com.clayton.ordermanagementapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
