package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DeliveryInfo {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    long id;
    @OneToOne
    @JsonIgnore
    Order order;

    public DeliveryInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
