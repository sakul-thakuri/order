package com.example.order.controller;

import com.example.order.Pojos.OrderRequest;
import com.example.order.Pojos.OrderResponse;
import com.example.order.entity.Order;
import com.example.order.exceptions.Exceptions;
import com.example.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Api(value = "Order management")
public class OrderController {

    private OrderService orderService;

    OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    @ApiOperation(value = "Api to create an order")
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
        catch (NoSuchFieldException | NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    @ApiOperation(value = "get all the list of orders", response = Order.class)
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
    @ApiOperation(value = "fetch an order of provided id", response = Order.class)
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
    @ApiOperation(value = "cancel/ delete an order by id provided")
    public ResponseEntity<?> deleteOrder(@PathVariable ("orderId") Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>("delete successful", HttpStatus.OK);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{orderId}")
    @ApiOperation(value = "update an order details of provided id")
    public ResponseEntity<?> updateOrder (@PathVariable("orderId") Long orderId, @RequestBody OrderRequest orderRequest) {

        try {
            orderService.updateOrder(orderId, orderRequest);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
