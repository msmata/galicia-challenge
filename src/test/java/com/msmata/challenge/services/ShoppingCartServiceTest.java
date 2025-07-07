package com.msmata.challenge.services;

import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.exceptions.CartNotFoundException;
import com.msmata.challenge.repositories.DiscountRepository;
import com.msmata.challenge.repositories.ProductRepository;
import com.msmata.challenge.repositories.ShoppingCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private ProductRepository productRepository;

    private ShoppingCartService shoppingCartService;

    ShoppingCart shoppingCartResponse;

    @BeforeEach
    void setup() {
        shoppingCartService = new ShoppingCartService(shoppingCartRepository, productRepository, discountRepository);
    }

    @Test
    public void findByIdReturnsAShoppingCart() {
        givenAShoppingCartWithIdExistsInDB("1234");
        whenFindByIdIsExecuted("1234");
        thenAShoppingCartWithIdIsReturned("1234");
    }

    @Test
    public void findByIdDoesNotFindAShoppingCart() {
        givenAShoppingCartWithIdDoesNotExistInDB("123");

        try {
            whenFindByIdIsExecuted("123");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Carrito con id 123 no encontrado");
        }
    }

    @Test
    public void createCartReturnsAShoppingCart() {
        givenAShoppingCartWithUserIdIsCreated("12345");
        whenCreateCartIsExecuted("12345");
        thenAShoppingCartWithUserIdIsReturned("12345");
    }

    private void givenAShoppingCartWithUserIdIsCreated(String userId) {
        ShoppingCart mockShoppingCart = new ShoppingCart();
        mockShoppingCart.setUserId(userId);
        Mockito.when(shoppingCartRepository.save(Mockito.any(ShoppingCart.class))).thenReturn(mockShoppingCart);
    }

    private void givenAShoppingCartWithIdExistsInDB(String id) {
        ShoppingCart mockShoppingCart = new ShoppingCart();
        mockShoppingCart.setId(id);
        Mockito.when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(mockShoppingCart));
    }

    private void givenAShoppingCartWithIdDoesNotExistInDB(String id) {
        Mockito.when(shoppingCartRepository.findById(id)).thenThrow(new CartNotFoundException("Carrito con id 123 no encontrado"));
    }

    private void whenFindByIdIsExecuted(String id) {
        shoppingCartResponse = shoppingCartService.findById(id);
    }

    private void whenCreateCartIsExecuted(String userId) {
        shoppingCartResponse = shoppingCartService.createCart(userId);
    }

    private void thenAShoppingCartWithIdIsReturned(String id) {
        Assertions.assertEquals(shoppingCartResponse.getId(), id);
    }

    private void thenAShoppingCartWithUserIdIsReturned(String userid) {
        Assertions.assertEquals(shoppingCartResponse.getUserId(), userid);
    }
}
