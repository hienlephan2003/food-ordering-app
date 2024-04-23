package com.foodapp.foodorderingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.foodorderingapp.entity.OrderLineItem_GroupOption;

@Repository
public interface OrderLineItemGroupOptionJpaRepository extends JpaRepository<OrderLineItem_GroupOption, Long> {
    
}
