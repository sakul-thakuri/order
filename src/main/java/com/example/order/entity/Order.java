package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
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

    @OneToMany (mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
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

    public Order() {
    }

    public Order(long id, List<Product> products, String status, Date creationTime, List<LineItem> lineItems,
                 Customer customer, String purchaseType, double discountedServiceCharge, double grandTotal,
                 double lineSubTotal, double serviceCharge, double subTotal, double taxTotal, double total,
                 double discountTotal, double discountPercent, double productAmountToBeRefunded,
                 double taxAmountToBeRefunded, double discount, String hasPendingRefund, String hasRefundableProduct,
                 double totalRefund, boolean modifiable, boolean cancelable, long itemsCount, DeliveryInfo deliveryInfo) {
        this.id = id;
        this.products = products;
        this.status = status;
        this.creationTime = creationTime;
        this.lineItems = lineItems;
        this.customer = customer;
        this.purchaseType = purchaseType;
        this.discountedServiceCharge = discountedServiceCharge;
        this.grandTotal = grandTotal;
        this.lineSubTotal = lineSubTotal;
        this.serviceCharge = serviceCharge;
        this.subTotal = subTotal;
        this.taxTotal = taxTotal;
        this.total = total;
        this.discountTotal = discountTotal;
        this.discountPercent = discountPercent;
        this.productAmountToBeRefunded = productAmountToBeRefunded;
        this.taxAmountToBeRefunded = taxAmountToBeRefunded;
        this.discount = discount;
        this.hasPendingRefund = hasPendingRefund;
        this.hasRefundableProduct = hasRefundableProduct;
        this.totalRefund = totalRefund;
        this.modifiable = modifiable;
        this.cancelable = cancelable;
        this.itemsCount = itemsCount;
        this.deliveryInfo = deliveryInfo;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customerId) {
        this.customer = customerId;
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

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public long getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(long itemsCount) {
        this.itemsCount = itemsCount;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
