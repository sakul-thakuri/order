package com.example.order.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class FulfillmentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long storeId;
    String storeAddress;

    public FulfillmentDetails() {
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
}
