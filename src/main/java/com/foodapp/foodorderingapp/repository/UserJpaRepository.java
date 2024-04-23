package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.Address;
import com.foodapp.foodorderingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsername(String phoneNumber);
    User findUserByUsername(String username);


}
