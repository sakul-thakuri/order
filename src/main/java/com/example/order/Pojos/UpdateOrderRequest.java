package com.example.order.Pojos;

import java.util.HashMap;
import java.util.Map;

public class UpdateOrderRequest {

    private Map<Long, Integer> products = new HashMap<>();
    private Double discountPercent;
    private boolean cancellable;
    private boolean isRefundable;
    private boolean modifiable;
    private String fulfillmentOption;
    private long storeId;

}
