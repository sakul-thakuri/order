package com.example.order.service;

import com.example.order.entity.LineItem;
import com.example.order.entity.Order;
import com.example.order.entity.Product;
import com.example.order.repository.LineItemRepository;
import org.springframework.stereotype.Service;

@Service
public class LineItemService {

    private LineItemRepository lineItemRepository;

    public LineItemService (LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    public void saveLineItem (LineItem lineItem) {
        lineItemRepository.save(lineItem);
    }

    public LineItem findLineItemByproductAndOrder (Order order, Product product) {
       try {
           return lineItemRepository.findByProductAndOrder(order, product);
       }
       catch (NullPointerException e) {
           throw new NullPointerException("no lineItem found for given arguments");
       }
    }
}
