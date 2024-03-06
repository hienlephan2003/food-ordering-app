package com.foodapp.foodorderingapp.dto.user;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
    private String fullname;
    private String phoneNumber;
}
