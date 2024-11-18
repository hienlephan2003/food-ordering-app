package com.foodapp.foodorderingapp.service.dish_type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.foodapp.foodorderingapp.dto.dish.DishResponse;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeRequest;
import com.foodapp.foodorderingapp.dto.dish.FeaturedDish;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeWithDishResponse;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeOverview;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.repository.DishJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
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
    private final DishJpaRepository dishJpaRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<DishTypeWithDishResponse> getAllDishTypes() {
        return dishTypeJpaRepository.findAll().stream().map(item -> {
            DishTypeWithDishResponse dishTypeResponse = new DishTypeWithDishResponse();
            dishTypeResponse.setId(item.getId());
            dishTypeResponse.setName(item.getName());
            List<Dish> dishes = dishJpaRepository.findDishesByDishType(item, PageRequest.of(0, 5));
            List<DishResponse> dishResponses = dishes.stream()
                    .map(product -> modelMapper.map(product, DishResponse.class))
                    .toList();
            dishTypeResponse.setDishes(dishResponses);
            return dishTypeResponse;
        }).collect(Collectors.toList());
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
    public DishType create(DishTypeRequest dishTypeRequest) {
        DishType dishType = DishType.builder().name(dishTypeRequest.getName()).build();
        return dishTypeJpaRepository.save(dishType);
    }

    @Override
    public DishType update(long id, DishTypeRequest dishTypeRequest) throws Exception {
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
