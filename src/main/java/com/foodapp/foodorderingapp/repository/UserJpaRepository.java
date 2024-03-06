package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.Order;
import com.foodapp.foodorderingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
    
}
