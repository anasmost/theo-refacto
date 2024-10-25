package com.nimbleways.springboilerplate.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.nimbleways.springboilerplate.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @NonNull
    Optional<Order> findById(@NonNull Long orderId);
}
