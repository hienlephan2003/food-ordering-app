package com.foodapp.foodorderingapp.service.dishType;

import java.util.List;

import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dishType.DishTypeCreate;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.DishType;

public interface DishTypeService {
    List<DishType> getAllDishTypes();
    List<DishType> seed();
    DishType getOne(long id) throws Exception;
    DishType create(DishTypeCreate dishTypeCreate);
   DishType update(long id, DishTypeCreate dishTypeCreate) throws Exception;
    DishType delete(long id) throws Exception;
}
