package com.foodapp.foodorderingapp.controller;


import com.foodapp.foodorderingapp.dto.cart.CartItemRequest;
import com.foodapp.foodorderingapp.dto.cart.CartOfDishRequest;
import com.foodapp.foodorderingapp.dto.cart.MyCartRestaurantRequest;
import com.foodapp.foodorderingapp.dto.cart.RestaurantCartResponse;
import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.CartItem;
import com.foodapp.foodorderingapp.entity.Order;
import com.foodapp.foodorderingapp.security.UserPrinciple;
import com.foodapp.foodorderingapp.service.cart.CartService;
import com.foodapp.foodorderingapp.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<CartItem> upsertCart(@Valid @RequestBody CartItemRequest cartItemRequest){
        return ResponseEntity.ok(cartService.upsertCartItem(cartItemRequest));
    }
    @GetMapping("/dish_user")
    public ResponseEntity<List<CartItem>> getByDishAndUser(@Valid @RequestBody CartOfDishRequest request){

        return ResponseEntity.ok(cartService.getCartsByDish(request));
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<CartItem>> getByRestaurant(@RequestParam long id){
        long userId = ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(cartService.getCartByRestaurant(id, userId));
    }
    @GetMapping("/user")
    public ResponseEntity<List<CartItem>> getByDishAndUser(@RequestParam long userId){
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantCartResponse>> getRestaurantOfCart(){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(cartService.getRestaurantOfCart(userPrinciple.getUserId()));
    }
    @DeleteMapping ("/{id}")
    public void removeFromCate(@PathVariable long id) throws Exception {
        cartService.removeFromCart(id);
    }
    @PutMapping ("/{id}")
    public CartItem updateCart(@PathVariable long id, @RequestParam int quantity) throws Exception {
       return cartService.updateCart(quantity, id);
    }
}