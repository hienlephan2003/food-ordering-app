package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.CartItem;
import com.foodapp.foodorderingapp.entity.CartItem_GroupOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartGroupOptionRepository extends JpaRepository<CartItem_GroupOption, Long> {
}
