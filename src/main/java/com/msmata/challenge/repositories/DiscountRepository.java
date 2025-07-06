package com.msmata.challenge.repositories;

import com.msmata.challenge.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCategory(String category);
}
