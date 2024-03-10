package com.foodapp.foodorderingapp.dto.auth;

import lombok.*;



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
