package com.nimbleways.springboilerplate.services.implementations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository pr;

    @Autowired
    private OrderRepository or;

    @Autowired
    NotificationService ns;

    public ProcessOrderResponse processOrder(@PathVariable Long orderId) {
        Order order = or.findById(orderId).get();
        System.out.println(order);
        List<Long> ids = new ArrayList<>();
        ids.add(orderId);
        Set<Product> products = order.getItems();
        for (Product p : products) {
            switch (p.getType()) {
                case NORMAL:
                    if (p.getAvailable() > 0) {
                        p.setAvailable(p.getAvailable() - 1);
                        pr.save(p);
                    } else {
                        int leadTime = p.getLeadTime();
                        if (leadTime > 0) {
                            this.notifyDelay(leadTime, p);
                        }
                    }
                    break;
                case SEASONAL:
                    // Add new season rules
                    if ((LocalDate.now().isAfter(p.getSeasonStartDate())
                            && LocalDate.now().isBefore(p.getSeasonEndDate())
                            && p.getAvailable() > 0)) {
                        p.setAvailable(p.getAvailable() - 1);
                        pr.save(p);
                    } else {
                        this.handleSeasonalProduct(p);
                    }
                    break;
                case EXPIRABLE:
                    if (p.getAvailable() > 0 && p.getExpiryDate().isAfter(LocalDate.now())) {
                        p.setAvailable(p.getAvailable() - 1);
                        pr.save(p);
                    } else
                        this.handleExpiredProduct(p);
                    break;

                default:
                    break;
            }
        }

        return new ProcessOrderResponse(order.getId());
    }

    public void notifyDelay(int leadTime, Product p) {
        p.setLeadTime(leadTime);
        pr.save(p);
        ns.sendDelayNotification(leadTime, p.getName());
    }

    public void handleSeasonalProduct(Product p) {
        int leadTime = p.getLeadTime();
        if (LocalDate.now().isBefore(p.getSeasonStartDate())) {
            ns.sendOutOfStockNotification(p.getName());
            pr.save(p);
        } else if (LocalDate.now().plusDays(leadTime).isAfter(p.getSeasonEndDate())) {
            ns.sendOutOfStockNotification(p.getName());
            p.setAvailable(0);
            pr.save(p);
        } else if (p.getAvailable() == 0) {
            notifyDelay(leadTime, p);
        }
    }

    public void handleExpiredProduct(Product p) {
        ns.sendExpirationNotification(p.getName(), p.getExpiryDate());
        p.setAvailable(0);
        pr.save(p);
    }
}
