package com.foodapp.foodorderingapp.service.order;

import java.util.List;
import java.util.Optional;

import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.Order;

public interface OrderService {
    Order createNewOrder(OrderRequest order);

    Optional<Order> findById(String orderId);

    Optional<Order> findByTrackingId(String trackingId);
    List<Order> getByUser(Long userId);
    
}
