package com.example.order.repository;

import com.example.order.entity.LineItem;
import com.example.order.entity.Order;
import com.example.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, UUID> {

    @Query("select l from LineItem l where l.product = ?1 and l.order = ?2")
    LineItem findByProductAndOrder(Product product, Order order);
}
