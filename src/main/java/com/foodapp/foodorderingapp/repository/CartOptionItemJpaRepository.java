package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.CartItem;
import com.foodapp.foodorderingapp.entity.CartItem_OptionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartOptionItemJpaRepository  extends JpaRepository<CartItem_OptionItem, Long> {
}
