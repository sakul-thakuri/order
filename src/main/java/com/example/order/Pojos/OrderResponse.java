package com.example.order.Pojos;

import com.example.order.entity.Customer;
import com.example.order.entity.DeliveryInfo;
import com.example.order.entity.LineItem;
import com.example.order.entity.Product;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class OrderResponse {

    private long id;
    private List<Product> products = new ArrayList<>();
    private String status;
    private Date creationTime;
    private List<LineItem> lineItems;
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
    private DeliveryInfo deliveryInfo;

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

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public void setProductAmountToBeRefunded(double productAmountToBeRefunded) {
        this.productAmountToBeRefunded = productAmountToBeRefunded;
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


    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
