package com.foodapp.foodorderingapp.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    private UUID userId;
    private String username;
    private String password;
    private String fullname;
    private String phoneNumber;
}
