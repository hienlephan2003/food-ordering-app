package com.foodapp.foodorderingapp.controller;


import com.foodapp.foodorderingapp.dto.cart.CartItemRequest;
import com.foodapp.foodorderingapp.dto.cart.CartOfDishRequest;
import com.foodapp.foodorderingapp.dto.cart.MyCartRestaurantRequest;
import com.foodapp.foodorderingapp.dto.cart.RestaurantCartResponse;
import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.CartItem;
import com.foodapp.foodorderingapp.entity.Order;
import com.foodapp.foodorderingapp.service.cart.CartService;
import com.foodapp.foodorderingapp.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<CartItem> addtoCart(@Valid @RequestBody CartItemRequest cartItemRequest){
        System.out.println(cartItemRequest);
        return ResponseEntity.ok(cartService.addItemToCart(cartItemRequest));
    }
    @GetMapping("/dish_user")
    public ResponseEntity<List<CartItem>> getByDishAndUser(@Valid @RequestBody CartOfDishRequest request){

        return ResponseEntity.ok(cartService.getCartsByDish(request));
    }
    @GetMapping("/restaurant_user")
    public ResponseEntity<List<CartItem>> getByRestaurnat(@Valid @RequestBody MyCartRestaurantRequest request){

        return ResponseEntity.ok(cartService.getCartByRestaurant(request));
    }
    @GetMapping("/user")
    public ResponseEntity<List<CartItem>> getByDishAndUser(@RequestParam long userId){
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }
    @GetMapping("/restaurant")
    public ResponseEntity<List<RestaurantCartResponse>> getRestaurantOfCart(@RequestParam long userId){
        return ResponseEntity.ok(cartService.getRestaurantOfCart(userId));
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