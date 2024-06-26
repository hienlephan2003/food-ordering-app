package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.dto.dish.DishSearch;
import com.foodapp.foodorderingapp.entity.Category;
import com.foodapp.foodorderingapp.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DishJpaRepository extends JpaRepository<Dish, Long> {
    List<Dish> findDishesByCategory(Category category);
    @Query("select new com.foodapp.foodorderingapp.dto.dish.DishSearch(d.id, d.name) from Dish d where d.name LIKE %?1%")
    List<DishSearch> search(String keyword);
    List<Dish> findDishesByRestaurantId(long restaurantId);
}
