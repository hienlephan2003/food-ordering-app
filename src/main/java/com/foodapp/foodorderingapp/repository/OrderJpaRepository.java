package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.User;
import org.springframework.stereotype.Repository;

import com.foodapp.foodorderingapp.entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;;
@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findByUser(Long userId);
}
