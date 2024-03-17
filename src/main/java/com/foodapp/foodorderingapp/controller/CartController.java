package com.foodapp.foodorderingapp.controller;


import com.foodapp.foodorderingapp.dto.cart.CartItemRequest;
import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.CartItem;
import com.foodapp.foodorderingapp.entity.Order;
import com.foodapp.foodorderingapp.service.cart.CartService;
import com.foodapp.foodorderingapp.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<CartItem> addtoCart(@Valid @RequestBody CartItemRequest cartItemRequest){
        return ResponseEntity.ok(cartService.addItemToCart(cartItemRequest));
    }
    @DeleteMapping ("/{id}")
    public void removeFromCate(@PathVariable long id) throws Exception {
        cartService.removeFromCart(id);
    }
}