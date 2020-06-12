package com.example.order.service;

import com.example.order.Pojos.OrderRequest;
import com.example.order.Pojos.OrderResponse;
import com.example.order.entity.*;
import com.example.order.exceptions.Exceptions;
import com.example.order.repository.CustomerRepository;
import com.example.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final LineItemService lineItemService;
    private final CustomerRepository customerRepository;
    private final DeliveryInfoService deliveryInfoService;

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, ProductService productService, LineItemService lineItemService,
                        CustomerRepository customerRepository, DeliveryInfoService deliveryInfoService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.lineItemService = lineItemService;
        this.customerRepository = customerRepository;
        this.deliveryInfoService = deliveryInfoService;
    }

    public void saveOrder(OrderRequest orderRequest) throws NoSuchFieldException {
        long itemsCount = 0;
        boolean cancellable = orderRequest.isCancellable();
        double discountedServiceCharge = 2;
        double grandTotal = 0;
        double serviceCharge = 10;
        double taxTotal = 0;
        double total = 0;
        double discountTotal = 0;
        double discountPercent = orderRequest.getDiscountPercent();
        double discount = 0;
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
            productList.add(product);
            logger.info(product.getName());

            productPrice = product.getPrice();

            total += (productPrice * quantity);
            taxTotal += (productPrice * quantity * taxPercent)/100;
            discount += ((productPrice * quantity * discountPercent)/100);

            lineItems.add(createLineItem(product, quantity, isRefundable, order));
            itemsCount += quantity;
        }

        discountTotal = discount + discountedServiceCharge;
        grandTotal = total + taxTotal + serviceCharge - discountTotal;

        try {
            DeliveryInfo deliveryInfo = deliveryInfoService.saveDeliveryInfo(orderRequest, order);
            order.setDeliveryInfo(deliveryInfo);
        }
        catch (NoSuchFieldException e) {
            logger.error(e.getMessage());
            throw new NoSuchFieldException(e.getMessage());
        }
        catch (NullPointerException e) {
            logger.error(e.getMessage());
            throw new NullPointerException(e.getMessage());
        }

        order.setDiscountPercent(discountPercent);
        order.setDiscountedServiceCharge(discountedServiceCharge);
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
        order.setServiceCharge(serviceCharge);

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
        List<Order> orders;
        try {
            orders = orderRepository.findAll();
        }
        catch (NullPointerException e) {
            throw new NullPointerException("there are no orders");
        }
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
            throw new IllegalArgumentException("Order found null");
        }
    }

    public void updateOrder(Long orderId, OrderRequest orderRequest) throws Exception {
        Order order = findOrder(orderId);
        if(order == null) {
            throw new NullPointerException("order not found");
        }
        if(!order.isModifiable()) {
            throw new Exception("cannot modify this order");
        }
        for(Map.Entry<Long, Integer> map : orderRequest.getProducts().entrySet()) {
            Product product = productService.findById(map.getKey());
            Integer quantity = map.getValue();
            if(product == null) {
                continue;
            }
            if(order.getProducts().contains(product)) {
                LineItem lineItem;
                try {
                     lineItem = lineItemService.findLineItemByproductAndOrder(order, product);
                     lineItem.setQuantity(quantity);
                     lineItemService.saveLineItem(lineItem);
                }
                catch (NullPointerException e) {
                    LineItem newLineItem = createLineItem(product, quantity, orderRequest.isRefundable(), order);
                    newLineItem.setQuantity(quantity);
                    lineItemService.saveLineItem(newLineItem);
                }
            }
            else {
                List<Product> productList = order.getProducts();
                productList.add(product);
                LineItem lineItem = createLineItem(product, quantity, orderRequest.isRefundable(), order);
                List<LineItem> lineItems = order.getLineItems();
                lineItems.add(lineItem);

                orderRepository.save(order);
            }
        }
        double total =0.0;
        double taxTotal = 0.0;
        double taxPercent = 4.0;
        double discount = 0.0;
        double discountTotal;
        double grandTotal;
        for(LineItem lineItem : order.getLineItems()) {
            double price = lineItem.getUnitPrice();
            int quantity = lineItem.getQuantity();
            total += price * quantity;
            taxTotal += (price * quantity * taxPercent)/100;
            discount += (price * quantity * order.getDiscountPercent())/100;
        }
        discountTotal = discount + order.getDiscountedServiceCharge();
        grandTotal = total + taxTotal + order.getServiceCharge() - discountTotal;


        try {
            DeliveryInfo deliveryInfo = deliveryInfoService.saveDeliveryInfo(orderRequest, order);
            order.setDeliveryInfo(deliveryInfo);
        }
        catch (NullPointerException e) {
            logger.error(e.getMessage());
            throw new NullPointerException(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

        order.setCancelable(orderRequest.isCancellable());
        order.setModifiable(orderRequest.isModifiable());
        order.setTotal(total);
        order.setTaxTotal(taxTotal);
        order.setDiscountPercent(discount);
        order.setGrandTotal(grandTotal);
        order.setDiscountTotal(discountTotal);

        orderRepository.save(order);
     }
}
