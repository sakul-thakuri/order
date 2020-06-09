package com.example.order.controller;

import com.example.order.Pojos.OrderRequest;
import com.example.order.Pojos.OrderResponse;
import com.example.order.entity.Customer;
import com.example.order.entity.Order;
import com.example.order.exceptions.Exceptions;
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
    private CustomerRepository customerRepository;

    OrderController (OrderService orderService, CustomerRepository customerRepository) {
        this.orderService = orderService;
        this.customerRepository = customerRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> placeOrder (@RequestBody OrderRequest orderRequest) {

        if(orderRequest.getCustomerId() == null || orderRequest.getProducts() == null) {
            return new ResponseEntity<>("customerId or products found null", HttpStatus.BAD_REQUEST);
        }
        try {
            orderService.saveOrder(orderRequest);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        catch (Exceptions.CustomerNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>("customer not found", HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getOrders () {
        List<OrderResponse> orderList = orderService.getOrders();

        if(orderList == null) {
            return new ResponseEntity<>("there are no orders", HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseEntity.ok(orderList);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getSingleOrder (@PathVariable("orderId") Long orderId) {
        OrderResponse order = orderService.findOneOrder(orderId);
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
