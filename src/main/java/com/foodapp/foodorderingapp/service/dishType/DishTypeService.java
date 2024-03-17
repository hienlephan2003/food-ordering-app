package com.foodapp.foodorderingapp.service.dishType;

import java.util.List;

import com.foodapp.foodorderingapp.entity.DishType;

public interface DishTypeService {
   
//    Dish_GroupOption addGroupOptionToDish(long dishId, long optionId) throws Exception;
    List<DishType> getAllDishTypes();
    
}
