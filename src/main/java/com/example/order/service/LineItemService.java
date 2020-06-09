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

    public void deleteLineItem (LineItem lineItem) {
        try {
            this.lineItemRepository.delete(lineItem);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("lineItem not found");
        }
    }
    public LineItem findLineItemByproductAndOrder (Product product, Order order) {
       return lineItemRepository.findByProductAndOrder(product, order);
    }
}
