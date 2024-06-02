package com.foodapp.foodorderingapp.mapper;

import com.foodapp.foodorderingapp.dto.dish.DishResponse;
import com.foodapp.foodorderingapp.entity.Dish;

public class DishMapper {
    public  static DishResponse toDishResponse(Dish dish) {
        return DishResponse.builder()
                .id(dish.getId())
                .name(dish.getName())
                .price(dish.getPrice())
                .dishType(dish.getDishType())
                .categoryId(dish.getCategory().getId())
                .restaurantId(dish.getRestaurant().getId())
                .description(dish.getDescription())
                .imageUrl(dish.getImageUrl())
                .status(dish.getStatus())

                .build();
    }
}
