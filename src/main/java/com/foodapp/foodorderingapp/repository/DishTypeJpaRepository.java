package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.DishType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DishTypeJpaRepository extends JpaRepository<DishType, Long> {
    @Query("SELECT DISTINCT dt FROM DishType dt LEFT JOIN FETCH dt.dishes")
    List<DishType> findTypeWithFeaturedDishes();
}
