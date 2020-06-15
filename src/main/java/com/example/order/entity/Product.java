package com.example.order.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private Double price;

    @ManyToMany
    private List<FulfillmentOptions> fulfillmentOptions;

    @ManyToMany
    private List<FulfillmentDetails> fulfillmentDetails;

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable (name = "product_category",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn (name = "category_id")})
    private Set<Categories> categories = new HashSet<>();

}
