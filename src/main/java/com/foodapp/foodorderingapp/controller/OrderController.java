package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.Order;
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

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/user")
    public ResponseEntity<List<Order>> getMyOrder(@RequestParam Long userId){
       return ResponseEntity.ok( orderService.getByUser(userId));
    }
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest orderRequest){
       return ResponseEntity.ok( orderService.createNewOrder(orderRequest));
    }
}
