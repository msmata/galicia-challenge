package com.msmata.challenge.services;

import com.msmata.challenge.entities.Discount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.msmata.challenge.entities.Product;
import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.repositories.ProductRepository;
import com.msmata.challenge.repositories.ShoppingCartRepository;
import com.msmata.challenge.repositories.DiscountRepository;
import com.msmata.challenge.exceptions.CartNotFoundException;
import com.msmata.challenge.exceptions.ProductNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    private final ShoppingCartRepository cartRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    public ShoppingCartService(ShoppingCartRepository cartRepository, ProductRepository productRepository, DiscountRepository discountRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
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

    @Async
    public void processOrder(String cartId) {
        logger.info("Inicio del procesamiento asincrónico del carrito: {}", cartId);

        cartRepository.findByIdWithProducts(cartId).ifPresent(cart -> {
            double total = 0.0;

            for (Product p : cart.getProducts()) {
                double discount = discountRepository.findByCategory(p.getCategory())
                    .map(Discount::getPercent)
                    .orElse(0.0);
                double priceWithDiscount = p.getPrice() * (1 - discount);
                total += priceWithDiscount;
            }

            logger.info("Descuento aplicado. Nuevo total: {}", total);

            try {
                logger.info("Validando y procesando orden del carrito {}", cartId);
                Thread.sleep(3000);
                logger.info("Orden procesada correctamente para el usuario {}", cart.getUserId());
            } catch (InterruptedException e) {
                logger.error("Error en el procesamiento del carrito {}", cartId, e);
                Thread.currentThread().interrupt();
            }
        });
    }
}
