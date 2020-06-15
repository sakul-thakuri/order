package com.example.order.Pojos;

import com.example.order.entity.Customer;
import com.example.order.entity.DeliveryInfo;
import com.example.order.entity.LineItem;
import com.example.order.entity.Product;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
