package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.dto.cart.CartOfDishRequest;
import com.foodapp.foodorderingapp.entity.CartItem;
import com.foodapp.foodorderingapp.entity.Dish_GroupOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartJpaRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c WHERE c.dish.id = :dishId and c.user.id = :userId")
    List<CartItem> findByDishAndUser(Long dishId, Long userId);
    @Query("SELECT c FROM CartItem c WHERE c.user.id = :userId")
    List<CartItem> findByUser(Long userId);
    @Query("SELECT c FROM CartItem c JOIN FETCH c.dish d JOIN FETCH d.restaurant WHERE c.user.id = :userId and d.restaurant.id = :restaurantId ORDER BY c.createdAt DESC ")
    List<CartItem> findByRestaurant(Long userId, Long restaurantId);
}
