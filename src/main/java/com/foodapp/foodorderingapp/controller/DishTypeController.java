package com.foodapp.foodorderingapp.controller;
import com.foodapp.foodorderingapp.dto.dish.DishByRestaurant;
import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dishType.DishTypeCreate;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.DishType;
import com.foodapp.foodorderingapp.service.dishType.DishTypeService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/dishType")
@RequiredArgsConstructor
public class DishTypeController {
    private final DishTypeService dishTypeService;
     @GetMapping("/dishes/{id}")
    public ResponseEntity<List<DishByRestaurant>> getDishesByDishType(@PathVariable long id) {
        return ResponseEntity.ok(dishTypeService.getDishes(id));
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
