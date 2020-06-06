package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.entity.Product;
import com.example.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrderController {

    private OrderService orderService;

    OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> putOrder (@RequestParam ("customerId") Long customerId,
                                       @RequestParam ("products") Map<Product, Integer> products) {
        orderService.saveOrder(customerId, products);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
