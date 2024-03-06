package com.foodapp.foodorderingapp.service;

import com.foodapp.foodorderingapp.dto.user.CreateUserRequest;
import com.foodapp.foodorderingapp.entity.User;
import com.foodapp.foodorderingapp.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    @Override
    public User createNewUser(CreateUserRequest createUserRequest) {
        User newUser = User.builder()
                .userId(UUID.randomUUID())
                .fullname(createUserRequest.getFullname())
                .password(createUserRequest.getPassword())
                .username(createUserRequest.getFullname())
                .phoneNumber(createUserRequest.getPhoneNumber())
                .build();
        return userJpaRepository.save(newUser);
    }
}
