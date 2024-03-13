package com.foodapp.foodorderingapp.service.restaurant;

import com.foodapp.foodorderingapp.dto.restaurant.RestaurantRequest;
import com.foodapp.foodorderingapp.entity.Restaurant;
import com.foodapp.foodorderingapp.enumeration.RestaurantStatus;

import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantRequest restaurant);
    Restaurant getRestaurantById(long id) throws IllegalArgumentException;
    List<Restaurant> getAllRestaurants() throws IllegalArgumentException;

    Restaurant updateRestaurant(long restaurantId, RestaurantRequest createRestaurantRequest);
    Restaurant changeStatus(long id, RestaurantStatus status) throws Exception;
}
