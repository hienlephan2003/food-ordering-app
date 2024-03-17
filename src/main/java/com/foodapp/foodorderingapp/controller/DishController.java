package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dish.Dish_GroupOptionRequest;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.security.UserPrinciple;
import com.foodapp.foodorderingapp.service.dish.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;
    @GetMapping("list/{id}")
    public ResponseEntity<List<Dish>> getAllDishs(@PathVariable long id){
        return ResponseEntity.ok(dishService.getDishesByCategory(id));
    }
    @PostMapping
    public ResponseEntity<Dish> createDish(@Valid @RequestBody DishRequest dishRequest) {
        return ResponseEntity.ok(dishService.addDish(dishRequest));
    }
    @PutMapping
    public ResponseEntity<Dish> updateDish(@Valid @RequestBody DishRequest dishRequest) throws Exception {
        return ResponseEntity.ok(dishService.updateDish(dishRequest.getDishId(), dishRequest));
    }
    @DeleteMapping
    public ResponseEntity<Boolean> deleteDish(@RequestBody long dishId) throws Exception {
        dishService.deleteDish(dishId);
        return ResponseEntity.ok(true);
    }
//    @PostMapping("/group_options")
//    public ResponseEntity<Dish_GroupOption> addGroupOptionToDish(@RequestBody Dish_GroupOptionRequest dish_groupOptionRequest) throws Exception {
//        return ResponseEntity.ok( dishService.addGroupOptionToDish(dish_groupOptionRequest.getDishId(), dish_groupOptionRequest.getGroupOptionId()));
//    }
    private void checkUserPermission(long userId){
        UserPrinciple userInfo = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userInfo.getUserId() !=  userId){
            throw new IllegalArgumentException("User doesn't have permission");
        }
    }

}
