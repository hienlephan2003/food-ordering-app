package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsUserByPhoneNumber(String phoneNumber);
    User findUserByUsername(String username);
}
