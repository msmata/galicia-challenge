package com.msmata.challenge.repositories;

import com.msmata.challenge.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    List<ShoppingCart> findByUserId(String userId);

    @Query("FROM ShoppingCart c JOIN FETCH c.products WHERE c.id = :id AND c.userId = :userId")
    Optional<ShoppingCart> findByIdWithProducts(@Param("id") String id, @Param("userId") String userId);
}
