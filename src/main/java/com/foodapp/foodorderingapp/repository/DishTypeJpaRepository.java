package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.DishType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishTypeJpaRepository extends JpaRepository<DishType, Long> {
}
