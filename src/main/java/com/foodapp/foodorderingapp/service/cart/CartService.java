package com.foodapp.foodorderingapp.service.cart;

import com.foodapp.foodorderingapp.dto.cart.CartItemRequest;
import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.CartItem;
import com.foodapp.foodorderingapp.entity.Order;

import java.util.Optional;

public interface CartService {
    CartItem addItemToCart(CartItemRequest cartItemRequest);
    boolean removeFromCart(long cartId) throws Exception;

}
