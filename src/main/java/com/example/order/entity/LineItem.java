package com.example.order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class LineItem {

    @Id
    private UUID id;

    private String description;
    private Double unitPrice;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
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
}
