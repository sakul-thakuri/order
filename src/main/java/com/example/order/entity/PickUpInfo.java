package com.example.order.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.TimeZone;

@Entity
public class PickUpInfo extends DeliveryInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long storeId;
    String address;
    @CreationTimestamp
    Date beginDate;
    Date endDate;
    TimeZone locationTimeZone;

    public PickUpInfo() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
