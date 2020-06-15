package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CancelledProducts {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    long id;
}
