package com.msmata.challenge.controllers;

import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.requests.CreateCartRequest;
import com.msmata.challenge.services.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable String id) {
        return ResponseEntity.ok(shoppingCartService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> createCart(@RequestBody CreateCartRequest request) {
        ShoppingCart saved = shoppingCartService.createCart(request.getUserId());
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{cartId}/product/{productId}")
    public ResponseEntity<ShoppingCart> updateCartProducts(@PathVariable String cartId, @PathVariable String productId) {
        ShoppingCart shoppingCart = shoppingCartService.addProductToCart(cartId, productId);
        return ResponseEntity.ok(shoppingCart);

    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<ShoppingCart> removeProduct(@PathVariable String cartId, @PathVariable String productId) {
        ShoppingCart shoppingCart = shoppingCartService.removeProduct(cartId, productId);
        return ResponseEntity.ok(shoppingCart);
    }
}
