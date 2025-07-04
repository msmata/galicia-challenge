package com.msmata.challenge.entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class ShoppingCart {

    @Id
    private String id;

    private String userId;

    @ManyToMany
    @JoinTable(
        name = "shopping_cart_product",
        joinColumns = @JoinColumn(name = "shopping_cart_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
