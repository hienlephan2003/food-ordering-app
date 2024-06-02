package com.foodapp.foodorderingapp.mapper;

import com.foodapp.foodorderingapp.dto.order.OrderResponse;
import com.foodapp.foodorderingapp.entity.Order;

public class OrderMapper {
    public static OrderResponse toOrderResponse(Order order){
      return  OrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .id(order.getId())
                .user(order.getUser())
                .price(order.getPrice())
                .restaurantId(order.getRestaurant().getId())
                .items(order.getItems())
                .failureMessages(order.getFailureMessages())
                .address(order.getAddress())
                .deliveryStatus(order.getDeliveryStatus())
                .build();
    }
}
