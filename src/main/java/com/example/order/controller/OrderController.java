package com.example.order.controller;

import com.example.order.entity.Customer;
import com.example.order.entity.Order;
import com.example.order.repository.CustomerRepository;
import com.example.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<?> placeOrder (@PathVariable ("customerId") Long customerId,
                                       @RequestBody Map<Long, Integer> products) {

        boolean result = orderService.saveOrder(customerId, products);
        if(result) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("sorry somethings gone wrong", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getOrders () {
        List<Order> orderList = orderService.getOrders();

        if(orderList == null) {
            return new ResponseEntity<>("there are no orders", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseEntity.ok(orderList);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getSingleOrder (@PathVariable("orderId") Long orderId) {
        Order order = orderService.findOrder(orderId);
        if(order ==null) {
            return new ResponseEntity<>("No order found with given id", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseEntity.ok(order);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable ("orderId") Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>("delete successful", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error occurred while deleting ", HttpStatus.BAD_GATEWAY);
        }
    }

//    @PutMapping("")
//    public ResponseEntity<?> updateOrder (@RequestBody Order order) {
//
//    }

}
