package com.foodapp.foodorderingapp.service;

import com.foodapp.foodorderingapp.dto.user.CreateUserRequest;
import com.foodapp.foodorderingapp.entity.User;

public interface UserService {
    User createNewUser(CreateUserRequest createUserRequest);
}
