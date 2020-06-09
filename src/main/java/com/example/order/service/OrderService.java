package com.example.order.service;

import com.example.order.Pojos.OrderRequest;
import com.example.order.Pojos.OrderResponse;
import com.example.order.entity.*;
import com.example.order.exceptions.Exceptions;
import com.example.order.repository.CustomerRepository;
import com.example.order.repository.FulfillmentDetailsRepository;
import com.example.order.repository.FulfillmentOptionRepository;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final LineItemService lineItemService;
    private final FulfillmentDetailsRepository fulfillmentDetailsRepository;
    private final FulfillmentOptionRepository fulfillmentOptionRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, ProductService productService, LineItemService lineItemService,
                        CustomerRepository customerRepository, FulfillmentOptionRepository fulfillmentOptionRepository,
                        FulfillmentDetailsRepository fulfillmentDetailsRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.lineItemService = lineItemService;
        this.customerRepository = customerRepository;
        this.fulfillmentDetailsRepository = fulfillmentDetailsRepository;
        this.fulfillmentOptionRepository = fulfillmentOptionRepository;
    }

    public void saveOrder(OrderRequest orderRequest) {
        long itemsCount = 0;
        boolean cancellable = orderRequest.isCancellable();
        double discountedServiceCharge = 2;
        double grandTotal = 0;
        double serviceCharge = 10;
        double taxTotal = 0;
        double total = 0;
        double discountTotal = 0;
        double discountPercent = orderRequest.getDiscountPercent();
        double productAmountToBeRefunded;
        double taxAmountToBeRefunded;
        double discount = 0;
        String hasPendingRefund;
        String hasRefundableProduct;
        double totalRefund;
        boolean modifiable = orderRequest.isModifiable();
        boolean isRefundable = orderRequest.isRefundable();
        List<LineItem> lineItems = new ArrayList<>();
        double taxPercent = 4;
        double productPrice = 0;

        Order order = new Order();
        List<Product> productList = new ArrayList<>();

        Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomerId());
        if(customer.isPresent()) {
            order.setCustomer(customer.get());
        }
        else {
           throw new Exceptions.CustomerNotFoundException("customer not found with given id");
        }

        for(Map.Entry<Long, Integer> map : orderRequest.getProducts().entrySet()) {
            Product product = productService.findById(map.getKey());
            if(product == null) {
                continue;
            }
            Integer quantity = map.getValue();
            if(!productList.contains(product)) {
                productList.add(product);
            }

            productPrice = product.getPrice();

            total += (productPrice * quantity);
            taxTotal += (productPrice * taxPercent)/100;
            discount += ((productPrice * discountPercent)/100);

            lineItems.add(createLineItem(product, quantity, isRefundable, order));
            itemsCount += quantity;

        }
        discountTotal = discount + discountedServiceCharge;

        grandTotal = total + taxTotal + serviceCharge - discountTotal;

        if(orderRequest.getFulfillmentOption().equals("homedelivery")) {
            order.setDeliveryInfo(createDeliveryInfo(order));
        }
        else if(orderRequest.getFulfillmentOption().equals("pickup")) {
            Optional<FulfillmentDetails> fulfillmentDetails = fulfillmentDetailsRepository.findById(orderRequest.getStoreId());
            if(fulfillmentDetails.isPresent()) {
                order.setDeliveryInfo(createPickUpInfo(order, fulfillmentDetails.get()));
            }
            else {
                throw new NullPointerException("store was not found");
            }

        }

        order.setDiscount(discount);
        order.setTaxTotal(taxTotal);
        order.setTotal(total);
        order.setDiscountTotal(discountTotal);
        order.setGrandTotal(grandTotal);
        order.setItemsCount(itemsCount);
        order.setCancelable(cancellable);
        order.setModifiable(modifiable);
        order.setProducts(productList);
        order.setLineItems(lineItems);

        try {
            orderRepository.save(order);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("order found null");
        }

    }


    public LineItem createLineItem (Product product, Integer quantity, boolean isRefundable, Order order) {
        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setDescription(product.getDescription());
        lineItem.setQuantity(quantity);
        lineItem.setRefundable(isRefundable);
        lineItem.setOrder(order);
        lineItem.setUnitPrice(product.getPrice());
        return lineItem;
    }


    public List<OrderResponse> getOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order : orders) {
            orderResponses.add(fillOrderResponse(order));
        }
        return orderResponses;
    }

    public OrderResponse findOneOrder (Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()) {
            return fillOrderResponse(order.get());
        }
        else return null;
    }

    public OrderResponse fillOrderResponse (Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setCustomer(order.getCustomer());
        orderResponse.setProducts(order.getProducts());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setCreationTime(order.getCreationTime());
        orderResponse.setLineItems(order.getLineItems());
        orderResponse.setItemsCount(order.getItemsCount());
        orderResponse.setCancelable(order.isCancelable());
        orderResponse.setModifiable(order.isModifiable());
        orderResponse.setTaxAmountToBeRefunded(order.getTaxAmountToBeRefunded());
        orderResponse.setPurchaseType(order.getPurchaseType());
        orderResponse.setTotalRefund(order.getTotalRefund());
        orderResponse.setHasRefundableProduct(order.getHasRefundableProduct());
        orderResponse.setHasPendingRefund(order.getHasPendingRefund());
        orderResponse.setDiscount(order.getDiscount());
        orderResponse.setDiscountedServiceCharge(order.getDiscountedServiceCharge());
        orderResponse.setGrandTotal(order.getGrandTotal());
        orderResponse.setServiceCharge(order.getServiceCharge());
        orderResponse.setSubTotal(order.getSubTotal());
        orderResponse.setTaxTotal(order.getTaxTotal());
        orderResponse.setTotal(order.getTotal());
        orderResponse.setLineSubTotal(order.getLineSubTotal());
        orderResponse.setServiceCharge(order.getServiceCharge());
        orderResponse.setDiscount(order.getDiscountTotal());
        orderResponse.setDiscountPercent(order.getDiscountPercent());
        orderResponse.setProductAmountToBeRefunded(order.getProductAmountToBeRefunded());
        orderResponse.setDeliveryInfo(order.getDeliveryInfo());

        return orderResponse;
    }

    public Order findOrder (Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElse(null);
    }


    public void deleteOrder(Long orderId) throws Exception {
        Order order = this.findOrder(orderId);
        if(order == null) {
            throw new NullPointerException("No order found for given id");
        }

        if(!order.isCancelable()) {
            throw new Exception("cannot cancel this order");
        }

        try {
            orderRepository.delete(order);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("error occurred");
        }
    }

    private DeliveryInfo createDeliveryInfo (Order order) {

        HomeDelivery homeDelivery = new HomeDelivery();
        homeDelivery.setAddress(order.getCustomer().getAddress());
        homeDelivery.setEndDate(Calendar.getInstance().getTime());
        homeDelivery.setLocationTimeZone(Calendar.getInstance().getTimeZone());
        homeDelivery.setOrder(order);

        return homeDelivery;
    }

    private DeliveryInfo createPickUpInfo(Order order, FulfillmentDetails fulfillmentDetails) {
        PickUpInfo pickUpInfo = new PickUpInfo();
        pickUpInfo.setAddress(fulfillmentDetails.getStoreAddress());
        pickUpInfo.setEndDate(Calendar.getInstance().getTime());
        pickUpInfo.setLocationTimeZone(Calendar.getInstance().getTimeZone());
        pickUpInfo.setOrder(order);
        pickUpInfo.setStoreId(fulfillmentDetails.getStoreId());

        return pickUpInfo;
    }



}
