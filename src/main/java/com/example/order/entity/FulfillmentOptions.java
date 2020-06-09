package com.example.order.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class FulfillmentOptions {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String fulfillmentType;

    public FulfillmentOptions() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFulfillmentType() {
        return fulfillmentType;
    }

    public void setFulfillmentType(String fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
    }
}
