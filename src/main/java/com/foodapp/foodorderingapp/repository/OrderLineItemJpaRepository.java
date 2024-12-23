package com.foodapp.foodorderingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.foodapp.foodorderingapp.entity.OrderLineItem;

@Repository
public interface OrderLineItemJpaRepository extends JpaRepository<OrderLineItem, Long> {
}
