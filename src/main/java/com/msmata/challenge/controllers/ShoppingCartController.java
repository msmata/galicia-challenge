package com.msmata.challenge.controllers;

import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.services.ShoppingCartService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carts")
@Api(tags = "Carrito de compras")
@ApiImplicitParams({
    @ApiImplicitParam(name = "Authorization", value = "Bearer {token}", required = true, dataType = "string", paramType = "header")
})
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtener un carrito de compras por id de carrito")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Carrito encontrado"),
            @ApiResponse(code = 404, message = "Carrito no encontrado"),
            @ApiResponse(code = 403, message = "Acceso prohibido a carrito")
    })
    public ResponseEntity<ShoppingCart> getCart(@ApiParam("ID del carrito") @PathVariable String id) {
        String userId = getAuthenticatedUserId();
        logger.info("GET /carts/{} solicitado por userId={}", id, userId);

        ShoppingCart cart = shoppingCartService.findById(id, userId);
        logger.info("Carrito encontrado: {}", cart.getId());

        return ResponseEntity.ok(cart);
    }

    @PostMapping
    @ApiOperation("Crear un carrito de compras para el userID loggeado")
    public ResponseEntity<ShoppingCart> createCart() {
        String userId = getAuthenticatedUserId();
        logger.info("POST /carts solicitado por userId={}", userId);

        ShoppingCart saved = shoppingCartService.createCart(userId);
        logger.info("Carrito creado: {}", saved.getId());

        URI location = URI.create("/carts/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{cartId}/product/{productId}")
    @ApiOperation("Agregar un producto a un carrito de compras")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Carrito actualizado"),
            @ApiResponse(code = 404, message = "Carrito no encontrado"),
            @ApiResponse(code = 404, message = "Producto no encontrado"),
            @ApiResponse(code = 403, message = "Acceso prohibido a carrito")
    })
    public ResponseEntity<ShoppingCart> updateCartProducts(@ApiParam("ID del carrito") @PathVariable String cartId,@ApiParam("ID del producto")  @PathVariable String productId) {
        String userId = getAuthenticatedUserId();
        logger.info("PUT /carts/{}/product/{} solicitado por userId={}", cartId, productId, userId);

        ShoppingCart shoppingCart = shoppingCartService.addProductToCart(cartId, productId, userId);
        logger.info("Producto {} agregado al carrito {}", productId, cartId);

        return ResponseEntity.ok(shoppingCart);
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    @ApiOperation("Remover un producto de un carrito de compras")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Carrito actualizado"),
            @ApiResponse(code = 404, message = "Carrito no encontrado"),
            @ApiResponse(code = 404, message = "Producto no encontrado"),
            @ApiResponse(code = 403, message = "Acceso prohibido a carrito")
    })
    public ResponseEntity<ShoppingCart> removeProduct(@ApiParam("ID del carrito") @PathVariable String cartId, @ApiParam("ID del producto") @PathVariable String productId) {
        String userId = getAuthenticatedUserId();
        logger.info("DELETE /carts/{}/product/{} solicitado por userId={}", cartId, productId, userId);

        ShoppingCart shoppingCart = shoppingCartService.removeProduct(cartId, productId, userId);
        logger.info("Producto {} removido del carrito {}", productId, cartId);

        return ResponseEntity.ok(shoppingCart);
    }

    @GetMapping("/user/")
    @ApiOperation("Listar los carritos de compras de un userID determinado")
    public ResponseEntity<List<ShoppingCart>> listShoppingCarts() {
        String userId = getAuthenticatedUserId();
        logger.info("GET /carts/user solicitado por userId={}", userId);

        List<ShoppingCart> userCarts = shoppingCartService.getUserCarts(userId);
        logger.info("Se encontraron {} carritos para userId={}", userCarts.size(), userId);

        return ResponseEntity.ok(userCarts);
    }

    @PostMapping("/{id}/process")
    @ApiOperation("Procesar los articulos de un determinado carrito de compras")
    public ResponseEntity<Map<String, String>> processCart(@ApiParam("ID del carrito") @PathVariable String id) {
        String userId = getAuthenticatedUserId();
        logger.info("POST /carts/{}/process solicitado por userId={}", id, userId);

        shoppingCartService.processOrder(id, userId);
        logger.info("Se inició el procesamiento asincrónico del carrito {}", id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Estamos procesando su orden");
        return ResponseEntity.accepted().body(response);
    }

    private String getAuthenticatedUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
