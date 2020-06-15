package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long storeId;
    String storeAddress;

}
