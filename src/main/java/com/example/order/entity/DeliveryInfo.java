package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInfo {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    long id;
    @OneToOne
    @JsonIgnore
    Order order;

}
