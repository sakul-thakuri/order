package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn (name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn (name = "order_id")
    @JsonIgnore
    Order order;
    private Integer quantity;
    private String description;
    private double unitPrice;
    private String specialInstruction;
    private String imageLocation;
    private boolean isRefundable;
    private long quantityRefunded;
    private long quantityRefundable;
    private long quantityCompletedRefunds;
    private long quantityPendingRefunds;
    // private List<FulfillmentStatus> fulfillmentStatuses;
}
