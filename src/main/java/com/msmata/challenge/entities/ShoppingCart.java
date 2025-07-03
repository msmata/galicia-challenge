package com.msmata.challenge.entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue
    private UUID id;

    private String userId;

    @ManyToMany
    @JoinTable(
        name = "shopping_cart_product",
        joinColumns = @JoinColumn(name = "shopping_cart_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
