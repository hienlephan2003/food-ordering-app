package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.restaurant.AddCategory;
import com.foodapp.foodorderingapp.dto.restaurant.RestaurantRequest;
import com.foodapp.foodorderingapp.dto.restaurant.RestaurantSearch;
import com.foodapp.foodorderingapp.entity.Dish;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantSearch>> search(@RequestParam String keyword) throws Exception {
        if (keyword.isEmpty())
            return ResponseEntity.ok(new ArrayList<>());
        return ResponseEntity.ok(restaurantService.search(keyword));
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(restaurantService.getAllRestaurants(pageNo, pageSize));
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Restaurant> addCategory(@RequestBody AddCategory restaurantRequest) {

        return ResponseEntity.ok(restaurantService.addCategory(restaurantRequest));
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantRequest));
    }

    @PutMapping
    public ResponseEntity<Restaurant> updateRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
        return ResponseEntity
                .ok(restaurantService.updateRestaurant(restaurantRequest.getRestaurantId(), restaurantRequest));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteRestaurant(@RequestBody long restaurantId) throws Exception {
        restaurantService.changeStatus(restaurantId, RestaurantStatus.DELETED);
        return ResponseEntity.ok(true);
    }
}
