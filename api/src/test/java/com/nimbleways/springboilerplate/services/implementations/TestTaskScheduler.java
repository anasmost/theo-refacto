package com.nimbleways.springboilerplate.services.implementations;

import javax.transaction.Transactional;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.testng.annotations.Test;
import com.nimbleways.springboilerplate.TaskSchedulerConfig;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.Product.ProductType;
import com.nimbleways.springboilerplate.entities.ProductListener;
import com.nimbleways.springboilerplate.repositories.ProductRepository;

// NOT WORKING PROPERLY
@ComponentScan(basePackageClasses = {TaskSchedulerConfig.class, ProductListener.class})
@DataJpaTest
public class TestTaskScheduler {
    @Mock
    private ProductRepository productRepository;

    @Mock
    TaskScheduler taskScheduler;
    @Mock
    ProductListener productListener;

    @Test
    @Transactional
    public void testTaskScheduler() {
        // GIVEN
        Product product =
                new Product(null, 15, 0, ProductType.NORMAL, "RJ45 Cable", null, null, null);

        // WHEN
        productRepository.save(product);

        // THEN
        Mockito.verify(productListener, Mockito.times(1)).decrementLeadTime(product);
    }
}
