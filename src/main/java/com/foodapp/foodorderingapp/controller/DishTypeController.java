package com.foodapp.foodorderingapp.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.foodapp.foodorderingapp.entity.DishType;
import com.foodapp.foodorderingapp.service.dishType.DishTypeService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
@RestController
@RequestMapping("/api/dishType")
@RequiredArgsConstructor
public class DishTypeController {
    private final DishTypeService dishTypeService;
    @GetMapping()
    public ResponseEntity<List<DishType>> getAll() {
        List<DishType> dishTypes = dishTypeService.getAllDishTypes();
        return ResponseEntity.ok(dishTypes);
        
    }
    
    
}
