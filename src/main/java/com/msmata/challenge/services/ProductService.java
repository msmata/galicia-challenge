package com.msmata.challenge.services;

import com.msmata.challenge.entities.Product;
import com.msmata.challenge.repositories.ProductRepository;

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
