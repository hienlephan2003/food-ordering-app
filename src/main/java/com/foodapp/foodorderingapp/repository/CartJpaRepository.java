package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<CartItem, Long> {

}
