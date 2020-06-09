package com.example.order.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.TimeZone;

@Entity
public class HomeDelivery extends DeliveryInfo{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    long id;
    String address;
    @CreationTimestamp
    Date beginDate;
    Date endDate;
    TimeZone locationTimeZone;

    public HomeDelivery() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TimeZone getLocationTimeZone() {
        return locationTimeZone;
    }

    public void setLocationTimeZone(TimeZone locationTimeZone) {
        this.locationTimeZone = locationTimeZone;
    }
}
