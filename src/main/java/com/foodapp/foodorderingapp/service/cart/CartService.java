package com.foodapp.foodorderingapp.service.cart;

import com.foodapp.foodorderingapp.dto.cart.CartItemRequest;
import com.foodapp.foodorderingapp.dto.cart.CartOfDishRequest;
import com.foodapp.foodorderingapp.dto.cart.MyCartRestaurantRequest;
import com.foodapp.foodorderingapp.dto.cart.RestaurantCartResponse;
import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.CartItem;
import com.foodapp.foodorderingapp.entity.Order;

import java.util.List;
import java.util.Optional;

public interface CartService {
    CartItem upsertCartItem(CartItemRequest cartItemRequest);
    boolean removeFromCart(long cartId) throws Exception;
    List<CartItem> getCartsByDish(CartOfDishRequest request);
    List<CartItem> getCartByUser(Long userId);
    List<CartItem> getCartByRestaurant(long restaurantId, long userId);

    CartItem updateCart(int quantity, long id);
    List<RestaurantCartResponse> getRestaurantOfCart(long userId);
}
