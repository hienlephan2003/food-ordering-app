package com.foodapp.foodorderingapp.service.dish_type;

import java.util.List;

import com.foodapp.foodorderingapp.dto.dish_type.DishTypeRequest;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeWithDishResponse;
import com.foodapp.foodorderingapp.entity.DishType;

public interface DishTypeService {
    List<DishTypeWithDishResponse> getAllDishTypes();
    List<DishType> seed();
    DishType create(DishTypeRequest dishTypeRequest);
    DishType update(long id, DishTypeRequest dishTypeRequest) throws Exception;
    DishType delete(long id) throws Exception;
}
