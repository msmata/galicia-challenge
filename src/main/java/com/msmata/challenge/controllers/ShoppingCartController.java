package com.msmata.challenge.controllers;

import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.requests.CreateCartRequest;
import com.msmata.challenge.services.ShoppingCartService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carts")
@Api(tags = "Carrito de compras")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtener un carrito de compras por id de carrito")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Carrito encontrado"),
            @ApiResponse(code = 404, message = "Carrito no encontrado")
    })
    public ResponseEntity<ShoppingCart> getCart(@ApiParam("ID del carrito") @PathVariable String id) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(shoppingCartService.findById(id, userId));
    }

    @PostMapping
    @ApiOperation("Crear un carrito de compras para un userID determinado")
    public ResponseEntity<ShoppingCart> createCart(@ApiParam("Request con ID del usuario") @RequestBody CreateCartRequest request) {
        ShoppingCart saved = shoppingCartService.createCart(request.getUserId());
        URI location = URI.create("/carts/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{cartId}/product/{productId}")
    @ApiOperation("Agregar un producto a un carrito de compras")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Carrito actualizado"),
            @ApiResponse(code = 404, message = "Carrito no encontrado"),
            @ApiResponse(code = 404, message = "Producto no encontrado")
    })
    public ResponseEntity<ShoppingCart> updateCartProducts(@ApiParam("ID del carrito") @PathVariable String cartId,@ApiParam("ID del producto")  @PathVariable String productId) {
        ShoppingCart shoppingCart = shoppingCartService.addProductToCart(cartId, productId);
        return ResponseEntity.ok(shoppingCart);
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    @ApiOperation("Remover un producto de un carrito de compras")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Carrito actualizado"),
            @ApiResponse(code = 404, message = "Carrito no encontrado"),
            @ApiResponse(code = 404, message = "Producto no encontrado")
    })
    public ResponseEntity<ShoppingCart> removeProduct(@ApiParam("ID del carrito") @PathVariable String cartId, @ApiParam("ID del producto") @PathVariable String productId) {
        ShoppingCart shoppingCart = shoppingCartService.removeProduct(cartId, productId);
        return ResponseEntity.ok(shoppingCart);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("Listar los carritos de compras de un userID determinado")
    public ResponseEntity<List<ShoppingCart>> listShoppingCarts(@ApiParam("ID del usuario") @PathVariable String userId) {
        List<ShoppingCart> userCarts = shoppingCartService.getUserCarts(userId);
        return ResponseEntity.ok(userCarts);
    }

    @PostMapping("/{id}/process")
    @ApiOperation("Procesar los articulos de un determinado carrito de compras")
    public ResponseEntity<Map<String, String>> processCart(@ApiParam("ID del carrito") @PathVariable String id) {
        shoppingCartService.processOrder(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Estamos procesando su orden");
        return ResponseEntity.accepted().body(response);
    }
}
