package com.foodapp.foodorderingapp.service.dish;

import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dish.DishResponse;
import com.foodapp.foodorderingapp.dto.dish.DishSearch;
import com.foodapp.foodorderingapp.dto.group_option.GroupOptionResponse;
import com.foodapp.foodorderingapp.entity.*;
import com.foodapp.foodorderingapp.enumeration.DishStatus;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final CategoryJpaRepository categoryJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final DishJpaRepository dishJpaRepository;
    private final DishTypeJpaRepository dishTypeJpaRepository;
    private final GroupOptionJpaRepository groupOptionJpaRepository;
    private final Dish_GroupOptionJpaRepository dish_groupOptionJpaRepository;
    private final ModelMapper modelMapper;
    @Override
    public DishResponse getDishById(long dishId) {
        Dish dish = dishJpaRepository.findById(dishId)
                .orElseThrow(() ->  new DataNotFoundException("Can't not find dish with id" + dishId));

        List<Dish_GroupOption> dishGroupOptions = dish_groupOptionJpaRepository.findByDishId(dishId);
        List<GroupOptionResponse> groupOptions = dishGroupOptions.stream()
                .map(dishGroupOption -> {
                    return modelMapper.map( dishGroupOption.getDish_groupOptionId().getGroupOption(), GroupOptionResponse.class);
                })
                .toList();
        DishResponse dishResponse = modelMapper.map(dish, DishResponse.class);
        dishResponse.setOptions(groupOptions);
        return dishResponse;
    }

    @Override
    @Transactional
    public Dish addDish(DishRequest dishRequest) {
        Restaurant existingRestaurant = restaurantJpaRepository
                .findById(dishRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find restaurant with id: " + dishRequest.getRestaurantId()));

        Category existingCategory = categoryJpaRepository
                .findById(dishRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find category with id: " + dishRequest.getCategoryId()));
        DishType dishType = dishTypeJpaRepository.findById(dishRequest.getDishTypeId()).orElseThrow(
                () -> new IllegalArgumentException("Can't not find dish type with id" + dishRequest.getDishTypeId()));
        Dish newDish = Dish.builder()
                .name(dishRequest.getName())
                .price(dishRequest.getPrice())
                .imageUrl(dishRequest.getImageUrl())
                .description(dishRequest.getDescription())
                .restaurant(existingRestaurant)
                .category(existingCategory)
                .dishType(dishType)
                .status(DishStatus.ACTIVE)
                .build();
        return dishJpaRepository.save(newDish);
    }

    @Override
    @Transactional
    public Dish updateDish(long id, DishRequest dishRequest) throws Exception {
        Dish dish = dishJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Can't find dish with id: " + id
        ));
        categoryJpaRepository
                .findById(dishRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find category with id: " + dishRequest.getCategoryId()));
        if (dishRequest.getName() != null && !dishRequest.getName().isEmpty()) {
            dish.setName(dishRequest.getName());
        }
        if (dishRequest.getDescription() != null && !dishRequest.getDescription().isEmpty()) {
            dish.setDescription(dishRequest.getDescription());
        }
        if (dishRequest.getImageUrl() != null && !dishRequest.getImageUrl().isEmpty()) {
            dish.setImageUrl(dishRequest.getImageUrl());
        }
        dish.setPrice(dishRequest.getPrice());
        return dish;
    }

    @Override
    @Transactional
    public Dish deleteDish(long id) throws Exception {
        Dish dish = dishJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Can't find dish with id: " + id
        ));
        dish.setStatus(DishStatus.DELETED);
        return dish;
    }

    @Override
    public Dish_GroupOption addGroupOptionToDish(long id, long groupOptionId) throws Exception {
        Dish dish = dishJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Can't find dish with id: " + id
        ));
        GroupOption groupOption = groupOptionJpaRepository.findById(groupOptionId)
                .orElseThrow(() -> new DataNotFoundException("Not found group optin with id" + groupOptionId));
        Dish_GroupOptionId dish_groupOptionId = Dish_GroupOptionId.builder()
                .dish(dish)
                .groupOption(groupOption)
                .build();
        Dish_GroupOption dish_groupOption = Dish_GroupOption.builder()
                .dish_groupOptionId(dish_groupOptionId)
                .build();
        return dish_groupOptionJpaRepository.save(dish_groupOption);
    }

    @Override
    public List<DishResponse> getDishesByCategory(long categoryId, int page, int limit) {
        Category existingCategory = categoryJpaRepository
                .findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find category with id: " + String.valueOf(categoryId)));
        return dishJpaRepository.findDishesByCategory(existingCategory, PageRequest.of(page, limit));
    }

    @Override
    public List<DishSearch> search(String keyword) {
        Pageable pageable = PageRequest.of(0, 5);
        return dishJpaRepository.search(keyword, pageable);
    }

    @Override
    public List<Dish> findAll() {
        List<Dish> dishes = dishJpaRepository.findAll();
        Hibernate.initialize(dishes);
        return dishes;
    }
    @Override
    public List<DishResponse> findDishesByRestaurant(long restaurantId, int limit, int page) {
        Restaurant restaurant = restaurantJpaRepository
                .findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find restaurant with id: " + String.valueOf(restaurantId)));
        Pageable request = PageRequest.of(page, limit);
        List<Dish> dishes = dishJpaRepository.findDishesByRestaurant(restaurant, request);
        return dishes.stream()
        .map(item -> modelMapper.map(item, DishResponse.class))
        .collect(Collectors.toList());
    }

    @Override
    public List<DishResponse> getDishesByDishType(long dishTypeId, int limit, int page) {
        DishType dishType = dishTypeJpaRepository
                .findById(dishTypeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find restaurant with id: " + String.valueOf(dishTypeId)));
        Pageable request = PageRequest.of(page, limit);
        List<Dish> dishes = dishJpaRepository.findDishesByDishType(dishType, request);
        return dishes.stream()
                .map(item -> modelMapper.map(item, DishResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DishResponse> getRecommendedDishes(List<Long> ids) {
        List<Dish> dishes = new ArrayList<>();
        ids.forEach(id -> {
            Dish dish = dishJpaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("not found dish"));
            dishes.add(dish);
        });
        return dishes.stream()
                .map(item -> modelMapper.map(item, DishResponse.class))
                .collect(Collectors.toList());
    }

}
