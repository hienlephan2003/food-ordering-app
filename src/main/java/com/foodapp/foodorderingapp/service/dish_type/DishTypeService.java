package com.foodapp.foodorderingapp.service.dish_type;

import java.util.List;

import com.foodapp.foodorderingapp.dto.dish.DishByRestaurant;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeCreate;
import com.foodapp.foodorderingapp.entity.DishType;

public interface DishTypeService {
    List<DishType> getAllDishTypes();
    List<DishType> seed();
    DishType getOne(long id) throws Exception;
    DishType create(DishTypeCreate dishTypeCreate);
   DishType update(long id, DishTypeCreate dishTypeCreate) throws Exception;
    DishType delete(long id) throws Exception;
    List<DishByRestaurant> getDishes(long dishTypeId);
}
