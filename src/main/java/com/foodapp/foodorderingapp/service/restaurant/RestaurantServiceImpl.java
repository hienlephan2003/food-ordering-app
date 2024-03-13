package com.foodapp.foodorderingapp.service.restaurant;

import com.foodapp.foodorderingapp.dto.restaurant.RestaurantRequest;
import com.foodapp.foodorderingapp.entity.Restaurant;
import com.foodapp.foodorderingapp.entity.User;
import com.foodapp.foodorderingapp.enumeration.RestaurantStatus;
import com.foodapp.foodorderingapp.repository.RestaurantJpaRepository;
import com.foodapp.foodorderingapp.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final UserJpaRepository userJpaRepository;
    @Override
    @Transactional
    public Restaurant createRestaurant(RestaurantRequest restaurantRequest) {
        Optional<User> user = userJpaRepository.findById(restaurantRequest.getOwnerId());
        if(user.isEmpty()) throw new IllegalArgumentException("Not found user");
        Restaurant restaurant = Restaurant.builder().name(restaurantRequest.getName())
                .imageUrl(restaurantRequest.getImageUrl())
                .name(restaurantRequest.getName())
                .coverImageUrl(restaurantRequest.getCoverImageUrl())
                .mainDish(restaurantRequest.getMainDish())
                .owner(user.get())
                .status(RestaurantStatus.CREATED)
                .categories(new ArrayList<>()).build();
        return restaurantJpaRepository.save(restaurant);
    }
    @Override
    public Restaurant getRestaurantById(long id) throws IllegalArgumentException {
        return restaurantJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @Override
    public List<Restaurant> getAllRestaurants() throws IllegalArgumentException {
        return restaurantJpaRepository.findAll();
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(long restaurantId, RestaurantRequest createRestaurantRequest) {
        Restaurant existingRestaurant = getRestaurantById(restaurantId);
        existingRestaurant.setName(createRestaurantRequest.getName());
        return restaurantJpaRepository.save(existingRestaurant);
    }


    @Override
    @Transactional
    public Restaurant changeStatus(long id, RestaurantStatus status) throws Exception {
         Restaurant restaurant = restaurantJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
         restaurant.setStatus(status);
         return restaurant;
    }

}
