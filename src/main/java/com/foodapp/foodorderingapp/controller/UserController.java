package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.dto.user.CreateUserRequest;
import com.foodapp.foodorderingapp.service.OrderService;
import com.foodapp.foodorderingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody CreateUserRequest createUserRequest){
        userService.createNewUser(createUserRequest);
    }
}
