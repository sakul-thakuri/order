package com.example.order;

import com.example.order.entity.*;
import com.example.order.repository.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import java.util.NoSuchElementException;
import java.util.Optional;


@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApiTest {

   @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @MockBean
    OrderRepository orderRepository;

    Logger logger = LoggerFactory.getLogger(OrderApiTest.class);
    OrderTestValues orderTestValues = new OrderTestValues();
    HttpHeaders headers = new HttpHeaders();

   @Nested
   class getOrders {
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
           when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.of(orderTestValues.getOrder()));
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

       @Test
       public void getOrderByIdWithException() {
           when(orderRepository.findById(anyLong())).thenThrow(NoSuchElementException.class);
           ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" +port + "/orders/1", String.class);
           assertNotEquals(HttpStatus.OK, response.getStatusCode());
           assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       }
   }

   @Nested
   class delete {

       HttpEntity<String> entity = new HttpEntity<String>(null, headers);

       @Test
       public void deleteOrder() {
           when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.of(orderTestValues.getOrder()));
           ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" +port + "/orders/1", HttpMethod.DELETE, entity, String.class);
           assertEquals(HttpStatus.OK, response.getStatusCode());
       }

       @Test
       public void deleteOrderWithCancellableAsFalse() {

           Order order = orderTestValues.getOrder();
           order.setCancelable(false);
           when(orderRepository.findById(anyLong())).thenReturn(java.util.Optional.of(order));
           ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" +port + "/orders/1", HttpMethod.DELETE, entity, String.class);
           assertNotEquals(HttpStatus.OK, response.getStatusCode());
           assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
       }
   }

}

