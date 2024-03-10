package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.User;
import org.springframework.stereotype.Repository;

import com.foodapp.foodorderingapp.entity.Order;



import org.springframework.data.jpa.repository.JpaRepository;;
@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    
}
