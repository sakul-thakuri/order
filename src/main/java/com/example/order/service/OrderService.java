package com.example.order.service;

import com.example.order.entity.Customer;
import com.example.order.entity.LineItem;
import com.example.order.entity.Order;
import com.example.order.entity.Product;
import com.example.order.repository.CustomerRepository;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductService productService;
    private LineItemService lineItemService;
    private CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, ProductService productService, LineItemService lineItemService,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.lineItemService = lineItemService;
        this.customerRepository = customerRepository;
    }

    public boolean saveOrder(Long customerId, Map<Long, Integer> products) {

        long itemsCount = 0;
        boolean cancellable = false;
        String purchaseType;
        double discountedServiceCharge = 2;
        double grandTotal = 0;
        double lineSubTotal;
        double serviceCharge = 4;
        double subTotal = 0;
        double taxTotal = 0;
        double total = 0;
        double discountTotal = 0;
        double discountPercent = 3.00;
        double productAmountToBeRefunded;
        double taxAmountToBeRefunded;
        double discount = 0;
        String hasPendingRefund;
        String hasRefundableProduct;
        double totalRefund;
        boolean modifiable = false;
        boolean isRefundable = false;
        List<LineItem> lineItems = new ArrayList<>();
        double taxPercent = 4;
        double productPrice = 0;

        Order order = new Order();
        List<Product> productList = new ArrayList<>();

        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()) {
            order.setCustomer(customer.get());
        }
        else {
           return false;
        }

        for(Map.Entry<Long, Integer> map : products.entrySet()) {
            Product product = productService.findById(map.getKey());
            long quantity = map.getValue();
            if(product != null) {
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

        orderRepository.save(order);
        return true;
    }


    public List<Order> getOrders() {
      return orderRepository.findAll();
    }

    public Order findOrder (Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElse(null);
    }

    public LineItem createLineItem (Product product, Long quantity, boolean isRefundable, Order order) {
        LineItem lineItem = new LineItem();
        lineItem.setId(UUID.randomUUID());
        lineItem.setProduct(product);
        lineItem.setDescription(product.getDescription());
        lineItem.setQuantity(quantity);
        lineItem.setRefundable(isRefundable);
        lineItem.setOrder(order);
        lineItem.setUnitPrice(product.getPrice());
        return lineItem;
    }


    public void deleteOrder(Long orderId) throws Exception {
        Order order = this.findOrder(orderId);
        if(order == null) {
            throw new NullPointerException("No order found for given id");
        }

        for(LineItem lineItem : order.getLineItems()) {
            lineItemService.deleteLineItem(lineItem);
        }

        try {
            orderRepository.delete(order);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("error occurred");
        }

    }
}
