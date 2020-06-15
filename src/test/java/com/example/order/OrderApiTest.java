package com.example.order;

import com.example.order.Pojos.OrderRequest;
import com.example.order.entity.*;
import com.example.order.entity.Order;
import com.example.order.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderApiTest {

   @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    LineItemRepository lineItemRepository;

    @MockBean
    FulfillmentDetailsRepository fulfillmentDetailsRepository;

    Logger logger = LoggerFactory.getLogger(OrderApiTest.class);
    OrderTestValues orderTestValues = new OrderTestValues();

    @BeforeAll
    public void setUp () {
        when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.of(orderTestValues.getOrder()));
        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(orderTestValues.getCustomer()));
        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(orderTestValues.getProduct()));
        when(fulfillmentDetailsRepository.findById(anyLong())).thenReturn(java.util.Optional.of(orderTestValues.getFulfillmentDetails()));
    }

   @Nested
   class get {
       @Test
       public void getOrders() {
           Order order = orderTestValues.getOrder();
           List<Order> orderList = new ArrayList<>();
           orderList.add(order);
           when(orderRepository.findAll()).thenReturn(orderList);
           ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" +port + "/orders",String.class);
           logger.info(response.getBody().toString());
           assertEquals(HttpStatus.OK, response.getStatusCode());
       }

       @Test
       public void getOrdersWithNullReturn() {
           when(orderRepository.findAll()).thenReturn(null);
           ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" +port + "/orders",String.class);
           assertNotEquals(HttpStatus.OK, response.getStatusCode());
           assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       }
   }

   @Nested
   class getOrder {
       @Test
       public void getOrderById() {
           ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" +port + "/orders/1", String.class);
           assertEquals(HttpStatus.OK, response.getStatusCode());
       }

       @Test
       public void getOrderByIdWithNullReturn() {
           when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
           ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" +port + "/orders/1", String.class);
           assertNotEquals(HttpStatus.OK, response.getStatusCode());
           assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       }
   }

   @Nested
   class delete {
       HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);

       @Test
       public void deleteOrder() {
           ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" +port + "/orders/1", HttpMethod.DELETE, entity, String.class);
           assertEquals(HttpStatus.OK, response.getStatusCode());
       }

       @Test
       public void deleteOrderWithCancellableAsFalse() {
           orderTestValues.getOrder().setCancelable(false);
           ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" +port + "/orders/1", HttpMethod.DELETE, entity, String.class);
           assertNotEquals(HttpStatus.OK, response.getStatusCode());
           assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       }
   }

   @Nested
   class createOrder {

       OrderRequest orderRequest = orderTestValues.getOrderRequest();

       @Test
       public void createOrder () {
           when(fulfillmentDetailsRepository.findById(anyLong())).thenReturn(java.util.Optional.of(orderTestValues.getFulfillmentDetails()));
           ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + port + "/orders", orderRequest, String.class);
           assertEquals(HttpStatus.OK, response.getStatusCode());
       }

       @Test
       public void createOrderWithInvalidCustomer () {
           when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
           ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + port + "/orders", orderRequest, String.class);
           assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       }

       @Test
       public void createOrderWithInvalidDeliveryOption () {
           orderRequest.setFulfillmentOption("abc");
           ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + port + "/orders", orderRequest, String.class);
           assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       }

       @Test
       public void createOrderWithEmptyFulfillmentDetails () {
           orderRequest.setFulfillmentOption("pickup");
           when(fulfillmentDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());
           ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + port + "/orders", orderRequest, String.class);
           assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       }
   }

   @Nested
   @TestInstance(TestInstance.Lifecycle.PER_CLASS)
   class update {

       HttpHeaders headers = new HttpHeaders();
       ObjectMapper mapper = new ObjectMapper();

       @BeforeAll
       public void setup () {

           when(lineItemRepository.findByProductAndOrder(any(Order.class), any(Product.class))).thenReturn(orderTestValues.getLineItem());
           headers.setContentType(MediaType.APPLICATION_JSON);
       }

       @Test
       public void updateOrder () throws JsonProcessingException {

           String orderRequestString = mapper.writeValueAsString(orderTestValues.getOrderRequest());
           HttpEntity<String> entity = new HttpEntity<String>(orderRequestString, headers);
           ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" +port + "/orders/1", HttpMethod.PUT, entity, String.class);
           assertEquals(HttpStatus.OK, response.getStatusCode());
       }

       @Test
       public void updateOrderWithModifiableFalse () throws JsonProcessingException {
           orderTestValues.getOrder().setModifiable(false);
           String orderRequestString = mapper.writeValueAsString(orderTestValues.getOrderRequest());
           HttpEntity<String> entity = new HttpEntity<String>(orderRequestString, headers);
           ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" +port + "/orders/1", HttpMethod.PUT, entity, String.class);
           assertNotEquals(HttpStatus.OK, response.getStatusCode());
           assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       }
   }
}

