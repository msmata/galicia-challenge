package com.msmata.challenge.controllers;

import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.repositories.ShoppingCartRepository;
import com.msmata.challenge.requests.CreateCartRequest;
import com.msmata.challenge.services.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

    private final ShoppingCartRepository cartRepository;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartRepository cartRepository, ShoppingCartService shoppingCartService) {
        this.cartRepository = cartRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public List<ShoppingCart> getAllCarts() {
        return cartRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable String id) {
        try {
            return ResponseEntity.ok(shoppingCartService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> createCart(@RequestBody CreateCartRequest request) {
        ShoppingCart saved = shoppingCartService.createCart(request.getUserId());
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{cartId}/product/{productId}")
    public ResponseEntity<ShoppingCart> updateCartProducts(@PathVariable String cartId, @PathVariable String productId) {
        try {
            ShoppingCart shoppingCart = shoppingCartService.addProductToCart(cartId, productId);
            return ResponseEntity.ok(shoppingCart);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<ShoppingCart> removeProduct(@PathVariable String cartId, @PathVariable String productId) {
        try {
            ShoppingCart shoppingCart = shoppingCartService.removeProduct(cartId, productId);
            return ResponseEntity.ok(shoppingCart);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
