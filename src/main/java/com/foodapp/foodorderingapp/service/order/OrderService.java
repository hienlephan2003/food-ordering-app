package com.foodapp.foodorderingapp.service.order;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.Order;
import com.foodapp.foodorderingapp.enumeration.OrderStatus;

public interface OrderService {
    Order createNewOrder(OrderRequest order);

    Optional<Order> findById(String orderId);

    Optional<Order> findByTrackingId(String trackingId);
List<Order> getByUser(Long userId);
    List<Order> getByRestaurantAndOrderStatus(Long restaurantId, OrderStatus orderStatus);
    List<Dish> getHotOrders(Long restaurantId);
    
}
