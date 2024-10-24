package com.nimbleways.springboilerplate.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nimbleways.springboilerplate.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long productId);

    Optional<Product> findFirstByName(String name);
}
