package com.example.order.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String ageRestricted;
    private boolean alcoholFlag;
    private String customerFacingSize;
    private String itemSizeUom;
    private String upc;
    private String taxGroupCode;
    private String prop65;
    private String ebtFlag;
    private String availableOnClickStatus;
    private String belowMinimumAdvertisedPrice;
    // private Set<FulfillmentOption> fulfillmentOptions = new HashSet<>();
    // private List<String> images = new ArrayList<>();
    // private Set<FulfillmentDetails> fulfillmentDetails = new HashSet<>();

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable (name = "product_category",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn (name = "category_id")})
    private Set<Categories> categories = new HashSet<>();


    @ManyToMany(mappedBy = "products")
    private Set<Order> orderList = new HashSet<>();


    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(Set<Order> orderList) {
        this.orderList = orderList;
    }

    public String getAgeRestricted() {
        return ageRestricted;
    }

    public void setAgeRestricted(String ageRestricted) {
        this.ageRestricted = ageRestricted;
    }

    public boolean isAlcoholFlag() {
        return alcoholFlag;
    }

    public void setAlcoholFlag(boolean alcoholFlag) {
        this.alcoholFlag = alcoholFlag;
    }

    public String getCustomerFacingSize() {
        return customerFacingSize;
    }

    public void setCustomerFacingSize(String customerFacingSize) {
        this.customerFacingSize = customerFacingSize;
    }

    public String getItemSizeUom() {
        return itemSizeUom;
    }

    public void setItemSizeUom(String itemSizeUom) {
        this.itemSizeUom = itemSizeUom;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getTaxGroupCode() {
        return taxGroupCode;
    }

    public void setTaxGroupCode(String taxGroupCode) {
        this.taxGroupCode = taxGroupCode;
    }

    public String getProp65() {
        return prop65;
    }

    public void setProp65(String prop65) {
        this.prop65 = prop65;
    }

    public String getEbtFlag() {
        return ebtFlag;
    }

    public void setEbtFlag(String ebtFlag) {
        this.ebtFlag = ebtFlag;
    }

    public String getAvailableOnClickStatus() {
        return availableOnClickStatus;
    }

    public void setAvailableOnClickStatus(String availableOnClickStatus) {
        this.availableOnClickStatus = availableOnClickStatus;
    }

    public String getBelowMinimumAdvertisedPrice() {
        return belowMinimumAdvertisedPrice;
    }

    public void setBelowMinimumAdvertisedPrice(String belowMinimumAdvertisedPrice) {
        this.belowMinimumAdvertisedPrice = belowMinimumAdvertisedPrice;
    }

    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
