package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.dto.dish.DishSearch;
import com.foodapp.foodorderingapp.entity.Category;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.Restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DishJpaRepository extends JpaRepository<Dish, Long> {
    List<Dish> findDishesByCategory(Category category);

    @Query("select new com.foodapp.foodorderingapp.dto.dish.DishSearch(d.id, d.name) from Dish d where d.name LIKE %?1%")
    List<DishSearch> search(String keyword, Pageable pageable);

    List<Dish> findDishesByRestaurant(Restaurant restaurant, Pageable pageable);
}
