package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dish.DishSearch;
import com.foodapp.foodorderingapp.dto.dish.Dish_GroupOptionRequest;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.Dish_GroupOption;
import com.foodapp.foodorderingapp.security.UserPrinciple;
import com.foodapp.foodorderingapp.service.dish.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;
    
    @PostMapping("/updateDishType/{id}")
    public ResponseEntity<Boolean> addDishType(@PathVariable long id)  {
        return ResponseEntity.ok(dishService.addDishType(id));
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<List<Dish>> getDishByCategoryId(@PathVariable long id){
        return ResponseEntity.ok(dishService.getDishesByCategory(id));
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<Dish>> getRecommendedDishes(@RequestParam List<Long> ids) throws Exception {
        return ResponseEntity.ok(dishService.getRecommendedDishes(ids));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable long id) throws Exception {
        return ResponseEntity.ok(dishService.getDishById(id));
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
    @GetMapping("/search")
    public ResponseEntity<List<DishSearch>> search(@RequestParam String keyword) throws Exception {
        if(keyword.isEmpty()) return ResponseEntity.ok(new ArrayList<>());
        return ResponseEntity.ok(dishService.search(keyword));
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Dish>> getAllByRestaurantId(@PathVariable long restaurantId, @RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize) throws Exception {
        return ResponseEntity.ok(dishService.findDishesByRestaurantId(restaurantId,pageNo, pageSize));
    }
    @GetMapping
    public ResponseEntity<List<Dish>> findAll() throws Exception {
        return ResponseEntity.ok(dishService.findAll());
    }
  

//    @PostMapping("/group_options")
//    public ResponseEntity<Dish_GroupOption> addGroupOptionToDish(@RequestBody Dish_GroupOptionRequest dish_groupOptionRequest) throws Exception {
//        return ResponseEntity.ok( dishService.addGroupOptionToDish(dish_groupOptionRequest.getDishId(), dish_groupOptionRequest.getGroupOptionId()));
//    }
    @PostMapping("/group_options")
    public ResponseEntity<Dish_GroupOption> addGroupOptionToDish(@RequestBody Dish_GroupOptionRequest dish_groupOptionRequest) throws Exception {
        return ResponseEntity.ok( dishService.addGroupOptionToDish(dish_groupOptionRequest.getDishId(), dish_groupOptionRequest.getGroupOptionId()));
    }
    private void checkUserPermission(long userId){
        UserPrinciple userInfo = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userInfo.getUserId() !=  userId){
            throw new IllegalArgumentException("User doesn't have permission");
        }
    }


}
