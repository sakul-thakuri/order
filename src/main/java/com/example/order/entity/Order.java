package com.example.order.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "order_product",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products = new ArrayList<>();

    private String status;

    @CreationTimestamp
    @Column (updatable = false)
    private Date creationTime;

   // private List<LineItem> lineItems;

    private long customerId;
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
    private String modifiable;
    private String cancelable;
    private Integer itemsCount;

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public double getDiscountedServiceCharge() {
        return discountedServiceCharge;
    }

    public void setDiscountedServiceCharge(double discountedServiceCharge) {
        this.discountedServiceCharge = discountedServiceCharge;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getLineSubTotal() {
        return lineSubTotal;
    }

    public void setLineSubTotal(double lineSubTotal) {
        this.lineSubTotal = lineSubTotal;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(double discountTotal) {
        this.discountTotal = discountTotal;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getProductAmountToBeRefunded() {
        return productAmountToBeRefunded;
    }

    public void setProductAmountToBeRefunded(double productAmoutToBeRefunded) {
        this.productAmountToBeRefunded = productAmoutToBeRefunded;
    }

    public double getTaxAmountToBeRefunded() {
        return taxAmountToBeRefunded;
    }

    public void setTaxAmountToBeRefunded(double taxAmountToBeRefunded) {
        this.taxAmountToBeRefunded = taxAmountToBeRefunded;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getHasPendingRefund() {
        return hasPendingRefund;
    }

    public void setHasPendingRefund(String hasPendingRefund) {
        this.hasPendingRefund = hasPendingRefund;
    }

    public String getHasRefundableProduct() {
        return hasRefundableProduct;
    }

    public void setHasRefundableProduct(String hasRefundableProduct) {
        this.hasRefundableProduct = hasRefundableProduct;
    }

    public double getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(double totalRefund) {
        this.totalRefund = totalRefund;
    }

    public String getModifiable() {
        return modifiable;
    }

    public void setModifiable(String modifiable) {
        this.modifiable = modifiable;
    }

    public String getCancelable() {
        return cancelable;
    }

    public void setCancelable(String cancelable) {
        this.cancelable = cancelable;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }
}
