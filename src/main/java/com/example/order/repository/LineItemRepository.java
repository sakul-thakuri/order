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

    @Query("select l from LineItem l where l.order = ?1 and l.product = ?2")
    LineItem findByProductAndOrder(Order order, Product product);
}
