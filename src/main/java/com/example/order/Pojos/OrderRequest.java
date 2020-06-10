package com.example.order.Pojos;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

public class OrderRequest {
    private Map<Long, Integer> products = new HashMap<>();
    private Long customerId;
    private Double discountPercent;
    private boolean cancellable;
    private boolean isRefundable;
    private boolean modifiable;
    @ApiModelProperty(notes = "requires either 'homedelivery' or 'pickup' as input")
    private String fulfillmentOption;
    private long storeId;

    public OrderRequest() {
    }

    public OrderRequest(Map<Long, Integer> products, Long customerId, Double discountPercent, boolean cancellable,
                        boolean isRefundable, boolean modifiable, String fulfillmentOption, long storeId) {
        this.products = products;
        this.customerId = customerId;
        this.discountPercent = discountPercent;
        this.cancellable = cancellable;
        this.isRefundable = isRefundable;
        this.modifiable = modifiable;
        this.fulfillmentOption = fulfillmentOption;
        this.storeId = storeId;
    }

    public Map<Long, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Integer> products) {
        this.products = products;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public boolean isRefundable() {
        return isRefundable;
    }

    public void setRefundable(boolean refundable) {
        isRefundable = refundable;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public String getFulfillmentOption() {
        return fulfillmentOption;
    }

    public void setFulfillmentOption(String fulfillmentOption) {
        this.fulfillmentOption = fulfillmentOption;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}
