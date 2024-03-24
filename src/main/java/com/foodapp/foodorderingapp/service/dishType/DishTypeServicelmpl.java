package com.foodapp.foodorderingapp.service.dishType;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodapp.foodorderingapp.entity.DishType;
import com.foodapp.foodorderingapp.repository.DishTypeJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DishTypeServicelmpl implements DishTypeService {
    private final DishTypeJpaRepository dishTypeJpaRepository;
    @Override
    public List<DishType> getAllDishTypes() {
        List<DishType> dishTypes = dishTypeJpaRepository.findAll();
       return dishTypes;
    }
    
}
