package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.Category;
import com.foodapp.foodorderingapp.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishJpaRepository extends JpaRepository<Dish, Long> {
    List<Dish> findDishesByCategory(Category category);
}
