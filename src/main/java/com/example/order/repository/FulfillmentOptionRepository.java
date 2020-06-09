package com.example.order.repository;

import com.example.order.entity.FulfillmentOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FulfillmentOptionRepository extends JpaRepository<FulfillmentOptions, Long> {
}
