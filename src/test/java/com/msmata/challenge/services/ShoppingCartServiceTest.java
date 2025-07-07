package com.msmata.challenge.services;

import com.msmata.challenge.entities.ShoppingCart;
import com.msmata.challenge.repositories.DiscountRepository;
import com.msmata.challenge.repositories.ProductRepository;
import com.msmata.challenge.repositories.ShoppingCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    ShoppingCart shoppingCartResponse;

    @Test
    public void findByIdReturnsAShoppingCart() {
        givenAShoppingCartWithIdExistsInDB("1234");
        whenFindByIdIsExecuted("1234");
        thenAShoppingCartWithIdIsReturned("1234");
    }

    private void givenAShoppingCartWithIdExistsInDB(String id) {
        ShoppingCart mockShoppingCart = new ShoppingCart();
        mockShoppingCart.setId(id);
        Mockito.when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(mockShoppingCart));
    }

    private void whenFindByIdIsExecuted(String id) {
        shoppingCartResponse = shoppingCartService.findById(id);
    }

    private void thenAShoppingCartWithIdIsReturned(String id) {
        Assertions.assertEquals(shoppingCartResponse.getId(), id);
    }
}
