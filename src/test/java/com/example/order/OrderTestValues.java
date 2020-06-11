package com.example.order;

import com.example.order.Pojos.OrderRequest;
import com.example.order.entity.*;

import java.util.*;

public class OrderTestValues {

    FulfillmentDetails fulfillmentDetails = new FulfillmentDetails(1, "kalimpong");
    FulfillmentOptions fulfillmentOptions = new FulfillmentOptions( 1, "homedelivery");
    
    Customer customer = new Customer((long) 1, "sakul", "kalimpong", null);

    Product product = new Product((long) 1, "mango", "its a fruit", "no",false,null, null, null, null, null,
    null, null, null, 10.00,
    new ArrayList<FulfillmentOptions>(){{add(fulfillmentOptions);}}, new ArrayList<FulfillmentDetails>(){{add(fulfillmentDetails);}},
    null);

    LineItem lineItem = new LineItem((long) 1, null, null, 10, null,11.00,
            null, null, true,0, 0, 0,0);

    Order order = new Order((long) 1, new ArrayList<Product>(){{add(product);}}, null, Calendar.getInstance().getTime(), null ,
    null, null, 10,100,0, 4, 0, 20, 70,
   10,10, 0,0,0,null, null,
    0, true,true, 40, new DeliveryInfo());

    Map<Long, Integer> map = new HashMap<Long, Integer>(){{put((long) 1,3);}};
    
    OrderRequest orderRequest = new OrderRequest(map, (long) 1, 4.0, true, true, true, "homedelivery", (long) 1);
    
    public FulfillmentDetails getFulfillmentDetails() {
        return fulfillmentDetails;
    }

    public void setFulfillmentDetails(FulfillmentDetails fulfillmentDetails) {
        this.fulfillmentDetails = fulfillmentDetails;
    }

    public FulfillmentOptions getFulfillmentOptions() {
        return fulfillmentOptions;
    }

    public void setFulfillmentOptions(FulfillmentOptions fulfillmentOptions) {
        this.fulfillmentOptions = fulfillmentOptions;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LineItem getLineItem() {
        return lineItem;
    }

    public void setLineItem(LineItem lineItem) {
        this.lineItem = lineItem;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
