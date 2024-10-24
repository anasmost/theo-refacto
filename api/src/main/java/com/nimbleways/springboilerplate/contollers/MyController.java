package com.nimbleways.springboilerplate.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.services.implementations.ProductService;

@RestController
@RequestMapping("/orders")
public class MyController {
    @Autowired
    private ProductService ps;

    @PostMapping("{orderId}/processOrder")
    // @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProcessOrderResponse> processOrder(@PathVariable Long orderId) {
        ps.processOrder(orderId);
        return new ResponseEntity<>(new ProcessOrderResponse(orderId), HttpStatus.OK);
    }

}
