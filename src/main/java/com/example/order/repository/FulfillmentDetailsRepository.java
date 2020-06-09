package com.example.order.repository;

import com.example.order.entity.FulfillmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FulfillmentDetailsRepository extends JpaRepository<FulfillmentDetails, Long> {
}
