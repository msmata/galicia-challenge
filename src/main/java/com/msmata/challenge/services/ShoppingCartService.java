package com.msmata.challenge.services;

import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.repositories.ShoppingCartRepository;

import java.util.List;
import java.util.UUID;

public class ShoppingCartService {

    private final ShoppingCartRepository cartRepository;

    public ShoppingCartService(ShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<ShoppingCart> all() {
        return cartRepository.findAll();
    }

    public ShoppingCart createCart(String userID) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(userID);

        return cartRepository.save(cart);
    }

    public void addProductToCart(UUID cartId, String productId) {
        ShoppingCart cart = cartRepository.findById(cartId).get();
    }
}
