package com.foodapp.foodorderingapp.service.dish_type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.foodapp.foodorderingapp.dto.dish.DishByRestaurant;
import com.foodapp.foodorderingapp.dto.dish.FeaturedDish;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeCreate;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeOverview;
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

        return dishTypeJpaRepository.findAll();
    }

    @Override
    public List<DishTypeOverview> getAllDishTypesWithTopThreeDishes() {
        
        List<DishTypeOverview> dishTypes = dishTypeJpaRepository.findAll().stream()
        .map(this::limitToTopThreeDishes)
        .collect(Collectors.toList());
        return dishTypes;
    }

    private DishTypeOverview limitToTopThreeDishes(DishType dishType) {
        List<FeaturedDish> limitedDishes = dishType.getDishes().stream()
        .limit(3)
        .map(dish -> {
            FeaturedDish newDish = new FeaturedDish();
            newDish.setId(dish.getId());
            newDish.setName(dish.getName());
            newDish.setDescription(dish.getDescription());
            newDish.setPrice(dish.getPrice());
            newDish.setImageUrl(dish.getImageUrl());
            return newDish;
        })
        .collect(Collectors.toList());
        System.out.println(limitedDishes);
        DishTypeOverview dishTypeOverview = new DishTypeOverview();
        dishTypeOverview.setId(dishType.getId());
        dishTypeOverview.setName(dishType.getName());
        dishTypeOverview.setDishes(limitedDishes);
        return dishTypeOverview;
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
    private DishByRestaurant convertToDto(Dish dish) {
        DishByRestaurant dto = new DishByRestaurant();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setPrice(dish.getPrice());
        dto.setDishType(dish.getDishType());
        dto.setImageUrl(dish.getImageUrl());
        dto.setStatus(dish.getStatus());

        // set other properties
        return dto;
    }
    @Override
    public List<DishByRestaurant> getDishes(long
     dishTypeId) {
        DishType dishType = dishTypeJpaRepository.findById(dishTypeId).get();
        return dishType.getDishes().stream()
                .limit(5)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
