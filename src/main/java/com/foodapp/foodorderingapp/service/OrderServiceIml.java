package com.foodapp.foodorderingapp.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.Order;
import com.foodapp.foodorderingapp.enumeration.OrderStatus;
import com.foodapp.foodorderingapp.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceIml implements OrderService {
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order createNewOrder(OrderRequest orderRequest) {
        Order order = Order.builder().id(UUID.randomUUID())
                .price(BigDecimal.valueOf(12)).
                orderStatus(OrderStatus.PENDING)
                .customerId(orderRequest.getUserId())
                .build();
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<Order> findById(String orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<Order> findByTrackingId(String trackingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTrackingId'");
    }
}
