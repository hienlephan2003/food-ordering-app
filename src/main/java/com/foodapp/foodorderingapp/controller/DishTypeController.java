package com.foodapp.foodorderingapp.controller;
import com.foodapp.foodorderingapp.dto.dish.DishByRestaurant;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeCreate;
import com.foodapp.foodorderingapp.dto.dish_type.DishTypeOverview;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.foodapp.foodorderingapp.entity.DishType;
import com.foodapp.foodorderingapp.service.dish_type.DishTypeService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/dish_types")
@RequiredArgsConstructor
public class DishTypeController {
    private final DishTypeService dishTypeService;
     @GetMapping("/dishes/{id}")
    public ResponseEntity<List<DishByRestaurant>> getDishesByDishType(@PathVariable long id) {
        return ResponseEntity.ok(dishTypeService.getDishes(id));
    }
    @GetMapping("/overview")
    public ResponseEntity<List<DishTypeOverview>> getDishTypesOverview() {
        
        return ResponseEntity.ok(dishTypeService.getAllDishTypesWithTopThreeDishes());
    }
    @GetMapping()
    public ResponseEntity<List<DishType>> getAll() {
        // List<DishType> dishTypes = dishTypeService.getAllDishTypes();
        
        return ResponseEntity.ok(dishTypeService.getAllDishTypes());
    }
   

    @PostMapping("/seed")
    public ResponseEntity<List<DishType>> seed() {
        return ResponseEntity.ok(dishTypeService.seed());
    }

    @PostMapping()
    public ResponseEntity<DishType> create(@Valid  @RequestBody DishTypeCreate dishTypeCreate) {
        return ResponseEntity.ok(dishTypeService.create(dishTypeCreate));
    }
}
