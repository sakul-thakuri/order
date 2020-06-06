package com.example.order.service;

import com.example.order.entity.Order;
import com.example.order.entity.Product;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean saveOrder(Long customerId, Map<Product, Integer> products) {

        Order order = new Order();
        order.setCustomerId(customerId);
        List<Product> productList = new ArrayList<>();

        for(Map.Entry<Product, Integer> map : products.entrySet()) {
            productList.add(map.getKey());
        }
        order.setProducts(productList);

        orderRepository.save(order);
        return true;
    }

}
