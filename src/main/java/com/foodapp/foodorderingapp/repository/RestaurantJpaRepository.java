package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long> {
}
