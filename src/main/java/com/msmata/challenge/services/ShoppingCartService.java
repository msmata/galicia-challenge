package com.msmata.challenge.services;

import java.util.List;

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
        ShoppingCart savedCart = cartRepository.save(cart);

        return savedCart;
    }

    public void addProductToCart(String cartId, String productId) {
        ShoppingCart cart = cartRepository.findById(cartId);
    }
}
