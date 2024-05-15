package com.foodapp.foodorderingapp.service.dishType;

import java.util.ArrayList;
import java.util.List;

import com.foodapp.foodorderingapp.dto.dishType.DishTypeCreate;
import com.foodapp.foodorderingapp.entity.Dish;
import org.springframework.stereotype.Service;

import com.foodapp.foodorderingapp.entity.DishType;
import com.foodapp.foodorderingapp.repository.DishTypeJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.javafaker.Faker;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishTypeServicelmpl implements DishTypeService {
    private final DishTypeJpaRepository dishTypeJpaRepository;

    @Override
    public List<DishType> getAllDishTypes() {
        List<DishType> dishTypes = dishTypeJpaRepository.findAll();
        
        return dishTypes;
    }

    @Override
    public DishType getOne(long id) throws Exception {
        return null;
    }

    @Override
    public DishType create(DishTypeCreate dishTypeCreate) {
        DishType dishType = DishType.builder().name(dishTypeCreate.getName()).build();
        return dishTypeJpaRepository.save(dishType);
    }

    @Override
    public DishType update(long id, DishTypeCreate dishTypeCreate) throws Exception {
        return null;
    }

    @Override
    public DishType delete(long id) throws Exception {
        return null;
    }

    @Override
    public List<DishType> seed() {
        List<DishType> dishTypes = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 9; i++) {
            DishType dishType = DishType.builder().name(faker.food().ingredient()).build();
            dishTypes.add(dishType);
            dishTypeJpaRepository.save(dishType);
        }
        return dishTypes;
    }

}
