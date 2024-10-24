package com.nimbleways.springboilerplate.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.nimbleways.springboilerplate.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @NonNull
    Optional<Product> findById(@NonNull Long productId);

    Optional<Product> findFirstByName(String name);
}
