package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.Order;
import com.foodapp.foodorderingapp.enumeration.OrderStatus;
import com.foodapp.foodorderingapp.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map.Entry;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getMyOrder(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getByUser(userId));
    }

    @GetMapping("/statistic/{restaurantId}")
    public ResponseEntity<List<Dish>> getStatistic(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getHotOrders(restaurantId));
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<Order>> getOrderByRestaurantId(@RequestParam Long restaurantId,
            @RequestParam OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.getByRestaurantAndOrderStatus(restaurantId, orderStatus));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createNewOrder(orderRequest));
    }
}
