package com.example.order.service;

import com.example.order.Pojos.OrderRequest;
import com.example.order.entity.*;
import com.example.order.exceptions.Exceptions;
import com.example.order.repository.FulfillmentDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class DeliveryInfoService {

    private FulfillmentDetailsRepository fulfillmentDetailsRepository;

    public DeliveryInfoService(FulfillmentDetailsRepository fulfillmentDetailsRepository) {
        this.fulfillmentDetailsRepository = fulfillmentDetailsRepository;
    }


    public DeliveryInfo saveDeliveryInfo(OrderRequest orderRequest, Order order) {
        if(orderRequest.getFulfillmentOption().equals("homedelivery")) {
            HomeDelivery homeDelivery = new HomeDelivery();
            homeDelivery.setAddress(order.getCustomer().getAddress());
            homeDelivery.setEndDate(Calendar.getInstance().getTime());
            homeDelivery.setLocationTimeZone(Calendar.getInstance().getTimeZone());
            homeDelivery.setOrder(order);
            return homeDelivery;
        }
        else if(orderRequest.getFulfillmentOption().equals("pickup")) {
            Optional<FulfillmentDetails> fulfillmentDetails = fulfillmentDetailsRepository.findById(orderRequest.getStoreId());
            if(fulfillmentDetails.isPresent()) {
                PickUpInfo pickUpInfo = new PickUpInfo();
                pickUpInfo.setAddress(fulfillmentDetails.get().getStoreAddress());
                pickUpInfo.setEndDate(Calendar.getInstance().getTime());
                pickUpInfo.setLocationTimeZone(Calendar.getInstance().getTimeZone());
                pickUpInfo.setOrder(order);
                pickUpInfo.setStoreId(fulfillmentDetails.get().getStoreId());
                return pickUpInfo;
            }
            else {
                throw new NullPointerException("store was not found");
            }
        }
       else throw new IllegalArgumentException("no delivery is available");
    }
}
