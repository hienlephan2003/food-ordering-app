package com.foodapp.foodorderingapp.service.dish;

import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dish.DishSearch;
import com.foodapp.foodorderingapp.entity.*;
import com.foodapp.foodorderingapp.enumeration.DishStatus;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final CategoryJpaRepository categoryJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final DishJpaRepository dishJpaRepository;
    private final DishTypeJpaRepository dishTypeJpaRepository;
    private final GroupOptionJpaRepository groupOptionJpaRepository;
    private final Dish_GroupOptionJpaRepository dish_groupOptionJpaRepository;

    @Override
    public Dish getDishById(long dishId) throws Exception {
        Optional<Dish> dish = dishJpaRepository.findById(dishId);
        if (dish.isPresent()) {
            return dish.get();
        } else
            throw new DataNotFoundException("Can't not find dish with id" + dishId);
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
        Dish dish = getDishById(id);
        if (dish != null) {
            Category existingCategory = categoryJpaRepository
                    .findById(dishRequest.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Cannot find category with id: " + dishRequest.getCategoryId()));
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
        return null;
    }

    @Override
    @Transactional
    public Dish deleteDish(long id) throws Exception {
        Dish dish = getDishById(id);
        dish.setStatus(DishStatus.DELETED);
        return dish;
    }

    @Override
    public Dish_GroupOption addGroupOptionToDish(long dishId, long groupOptionId) throws Exception {
        Dish dish = getDishById(dishId);
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
    public List<Dish> getDishesByCategory(long categoryId) {
        Category existingCategory = categoryJpaRepository
                .findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find category with id: " + String.valueOf(categoryId)));
        return dishJpaRepository.findDishesByCategory(existingCategory);
    }

    @Override
    public List<DishSearch> search(String keyword) {
        return dishJpaRepository.search(keyword);
    }

    @Override
    public List<Dish> findAll() {
        List<Dish> dishes = dishJpaRepository.findAll();
        Hibernate.initialize(dishes);
        return dishes;
    }

    @Override
    public List<Dish> findDishesByRestaurantId(long restaurantId, int pageNo,int pageSize ) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Dish> pagedResult =  dishJpaRepository.findDishesByRestaurantId(restaurantId, paging);
        if(pagedResult.hasContent()) {
            System.out.println(pagedResult.getContent().size());
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addDishType(long id) {
        List<Dish> dishs = dishJpaRepository.findAll();
        Category category = categoryJpaRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("not found category"));
        dishs.stream().forEach((dish) -> {
            if (dish.getCategory() == null) {
                Random random = new Random();
                Restaurant restaurant = restaurantJpaRepository.findById(Long.valueOf(random.nextInt(110) + 7))
                        .orElseThrow(() -> new DataNotFoundException("not found category"));
                DishType dishType = dishTypeJpaRepository.findById(Long.valueOf(random.nextInt(9) + 11))
                        .orElseThrow(() -> new DataNotFoundException("not found category"));
                dish.setCategory(category);
                dish.setRestaurant(restaurant);
                dish.setDishType(dishType);
                dishJpaRepository.save(dish);
            }
        });
        return true;
    }

    @Override
    public List<Dish> getRecommendedDishes(List<Long> ids) {
        List<Dish> dishes = new ArrayList<>();
        ids.forEach(id -> {
            Dish dish = dishJpaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("not found dish"));
            dishes.add(dish);
        });
        return dishes;
    }

}
