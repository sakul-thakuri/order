package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products = new ArrayList<>();

    private String status;

    @CreationTimestamp
    @Column (updatable = false)
    private Date creationTime;

    @OneToMany (mappedBy = "order", cascade = CascadeType.ALL)
    private List<LineItem> lineItems;

    @ManyToOne
    @JoinColumn (name = "customer_id")
    private Customer customer;
    private String purchaseType;
    private double discountedServiceCharge;
    private double grandTotal;
    private double lineSubTotal;
    private double serviceCharge;
    private double subTotal;
    private double taxTotal;
    private double total;
    private double discountTotal;
    private double discountPercent;
    private double productAmountToBeRefunded;
    private double taxAmountToBeRefunded;
    private double discount;
    private String hasPendingRefund;
    private String hasRefundableProduct;
    private double totalRefund;
    private boolean modifiable;
    private boolean cancelable;
    private long itemsCount;

    @OneToOne (cascade = {CascadeType.ALL})
    private DeliveryInfo deliveryInfo;
}
