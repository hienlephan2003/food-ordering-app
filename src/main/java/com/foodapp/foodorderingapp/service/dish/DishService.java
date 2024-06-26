package com.foodapp.foodorderingapp.service.dish;

import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dish.DishSearch;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.Dish_GroupOption;

import java.util.List;

public interface DishService {
    Dish getDishById(long dishId) throws Exception;
    Dish addDish(DishRequest dishRequest);
    Dish updateDish(long id, DishRequest dishRequest) throws Exception;
    Dish deleteDish(long id) throws Exception;
    Dish_GroupOption addGroupOptionToDish(long dishId, long optionId) throws Exception;
    List<Dish> getDishesByCategory(long categoryId);
    List<DishSearch> search(String keyword);
    List<Dish> findAll();
    List<Dish> findDishesByRestaurantId(long restaurantId);
    List<Dish> getRecommendedDishes(List<Long> ids);
    boolean addDishType(long id);
}
