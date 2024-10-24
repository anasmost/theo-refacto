package com.nimbleways.springboilerplate.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.Product.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.utils.Annotations.UnitTest;

@ExtendWith(SpringExtension.class)
@UnitTest
public class MyUnitTests {

    @Mock
    private NotificationService notificationService;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        Mockito.reset(notificationService, productRepository);
    }

    @Test
    public void testProductRepository() {
        // GIVEN
        Product product =
                new Product(null, 15, 0, ProductType.NORMAL, "RJ45 Cable", null, null, null);

        // WHEN
        productRepository.save(product);

        // THEN
        assertEquals(0, product.getAvailable());
        assertEquals(15, product.getLeadTime());
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    public void testProductService() {
        // GIVEN
        Product product =
                new Product(null, 15, 0, ProductType.NORMAL, "RJ45 Cable", null, null, null);

        // WHEN
        productService.notifyDelay(product.getLeadTime(), product);

        // THEN
        Mockito.verify(notificationService, Mockito.times(1))
                .sendDelayNotification(product.getLeadTime(), product.getName());
    }
}
