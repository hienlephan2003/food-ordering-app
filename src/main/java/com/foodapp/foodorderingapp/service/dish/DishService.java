package com.foodapp.foodorderingapp.service.dish;

import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dish.DishResponse;
import com.foodapp.foodorderingapp.dto.dish.DishSearch;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.Dish_GroupOption;

import java.util.List;

public interface DishService {
    DishResponse getDishById(long dishId) throws Exception;
    Dish addDish(DishRequest dishRequest);
    Dish updateDish(long id, DishRequest dishRequest) throws Exception;
    Dish deleteDish(long id) throws Exception;
    Dish_GroupOption addGroupOptionToDish(long dishId, long optionId) throws Exception;
    List<DishResponse> getDishesByCategory(long categoryId, int page, int limit);
    List<DishSearch> search(String keyword);
    List<Dish> findAll();
    List<DishResponse> findDishesByRestaurant(long restaurantId, int limit, int page);
    List<DishResponse> getDishesByDishType(long dishTypeId, int limit, int page);
    List<DishResponse> getRecommendedDishes(List<Long> ids);
}
