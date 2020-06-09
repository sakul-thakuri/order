package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class LineItem {

    @Id
    private UUID id;

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


    public LineItem() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public boolean isRefundable() {
        return isRefundable;
    }

    public void setRefundable(boolean refundable) {
        isRefundable = refundable;
    }

    public long getQuantityRefunded() {
        return quantityRefunded;
    }

    public void setQuantityRefunded(long quantityRefunded) {
        this.quantityRefunded = quantityRefunded;
    }

    public long getQuantityRefundable() {
        return quantityRefundable;
    }

    public void setQuantityRefundable(long quantityRefundable) {
        this.quantityRefundable = quantityRefundable;
    }

    public long getQuantityCompletedRefunds() {
        return quantityCompletedRefunds;
    }

    public void setQuantityCompletedRefunds(long quantityCompletedRefunds) {
        this.quantityCompletedRefunds = quantityCompletedRefunds;
    }

    public long getQuantityPendingRefunds() {
        return quantityPendingRefunds;
    }

    public void setQuantityPendingRefunds(long quantityPendingRefunds) {
        this.quantityPendingRefunds = quantityPendingRefunds;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
