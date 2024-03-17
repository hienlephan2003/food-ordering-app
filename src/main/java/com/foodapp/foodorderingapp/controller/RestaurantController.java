package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.restaurant.RestaurantRequest;
import com.foodapp.foodorderingapp.entity.Restaurant;
import com.foodapp.foodorderingapp.entity.User;
import com.foodapp.foodorderingapp.enumeration.RestaurantStatus;
import com.foodapp.foodorderingapp.security.UserPrinciple;
import com.foodapp.foodorderingapp.service.restaurant.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
    ){
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
        checkUserPermission(restaurantRequest.getOwnerId());
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantRequest));
    }
    @PutMapping
    public ResponseEntity<Restaurant> updateRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest){
        checkUserPermission(restaurantRequest.getOwnerId());
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantRequest.getRestaurantId(), restaurantRequest));
    }
    @DeleteMapping
    public ResponseEntity<Boolean> deleteRestaurant(@RequestBody long restaurantId) throws Exception {
        restaurantService.changeStatus(restaurantId, RestaurantStatus.DELETED);
        return ResponseEntity.ok(true);
    }
    private void checkUserPermission(long userId){
        UserPrinciple userInfo = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userInfo.getUserId() !=  userId){
            throw new IllegalArgumentException("User doesn't have permission");
        }
    }
}
