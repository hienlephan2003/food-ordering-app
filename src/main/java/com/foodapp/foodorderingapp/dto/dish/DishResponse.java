package com.foodapp.foodorderingapp.dto.dish;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.foodapp.foodorderingapp.entity.Category;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.DishType;
import com.foodapp.foodorderingapp.entity.Restaurant;
import com.foodapp.foodorderingapp.enumeration.DishStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class DishResponse {
    private Long id;
    private Long restaurantId;
    private Long categoryId;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private DishStatus status;
    private DishType dishType;
}
