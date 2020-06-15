package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeDelivery extends DeliveryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String address;
    @CreationTimestamp
    Date beginDate;
    Date endDate;
    TimeZone locationTimeZone;

}
