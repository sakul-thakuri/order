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
public class FulfillmentOptions {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String fulfillmentType;

}
