package com.msmata.challenge.services;

import java.util.List;

public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> all() {
        return repository.findAll();
    }
}
