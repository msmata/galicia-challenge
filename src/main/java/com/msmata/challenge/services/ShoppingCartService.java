package com.msmata.challenge.services;

import com.msmata.challenge.entities.Product;
import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.repositories.ProductRepository;
import com.msmata.challenge.repositories.ShoppingCartRepository;
import com.msmata.challenge.exceptions.CartNotFoundException;
import com.msmata.challenge.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Carrito con id " + cartId + " no encontrado"));
    }

    public ShoppingCart addProductToCart(String cartId, String productId) {
        Product product = productRepository.findById(Long.valueOf(productId)).orElseThrow(() -> new ProductNotFoundException("Producto con id " + productId + " no encontrado"));
        ShoppingCart cart = this.findById(cartId);

        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    public ShoppingCart removeProduct(String cartId, String productId) {
        ShoppingCart cart = this.findById(cartId);
        Product product = cart.getProducts().stream().filter(p -> p.getId().equals(Long.valueOf(productId))).findFirst().orElseThrow(() -> new ProductNotFoundException("Producto con id " + productId + " no encontrado"));
        cart.getProducts().remove(product);

        return cartRepository.save(cart);
    }

    public List<ShoppingCart> getUserCarts(String userId) {
        return cartRepository.findByUserId(userId);
    }
}
