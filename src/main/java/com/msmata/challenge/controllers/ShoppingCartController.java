package com.msmata.challenge.controllers;

import com.msmata.challenge.entities.Product;
import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.repositories.ProductRepository;
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
    private final ProductRepository productRepository;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartRepository cartRepository, ProductRepository productRepository, ShoppingCartService shoppingCartService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public List<ShoppingCart> getAllCarts() {
        return cartRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable UUID id) {
        return cartRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> createCart(@RequestBody CreateCartRequest request) {
        ShoppingCart saved = shoppingCartService.createCart(request.getUserId());
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{cartId}/product/{productId}")
    public ResponseEntity<ShoppingCart> updateCartProducts(@PathVariable String cartId, @PathVariable String productId) {
        /*
        return cartRepository.findById(id).map(cart -> {
            List<Product> products = productRepository.findAllById(productIds);
            cart.setProducts(products);
            return ResponseEntity.ok(cartRepository.save(cart));
        }).orElse(ResponseEntity.notFound().build());*/
        return ResponseEntity.ok(null);
    }
}
