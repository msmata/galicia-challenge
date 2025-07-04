package com.msmata.challenge.services;

import com.msmata.challenge.entities.Product;
import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.repositories.ProductRepository;
import com.msmata.challenge.repositories.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository cartRepository;
    private final ProductRepository productRepository;

    public ShoppingCartService(ShoppingCartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public ShoppingCart createCart(String userID) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(userID);

        return cartRepository.save(cart);
    }

    public ShoppingCart findById(String cartId) {
        return cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
    }

    public ShoppingCart addProductToCart(String cartId, String productId) {
        Product product = productRepository.findById(Long.valueOf(productId)).orElseThrow(RuntimeException::new);
        ShoppingCart cart = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);

        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    public ShoppingCart removeProduct(String cartId, String productId) {
        ShoppingCart cart = cartRepository.findById(cartId).orElseThrow(RuntimeException::new);
        Product product = cart.getProducts().stream().filter(p -> p.getId().equals(Long.valueOf(productId))).findFirst().orElseThrow(RuntimeException::new);
        cart.getProducts().remove(product);

        return cartRepository.save(cart);
    }
}
