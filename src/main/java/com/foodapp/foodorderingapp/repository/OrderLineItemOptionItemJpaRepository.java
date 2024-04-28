package com.foodapp.foodorderingapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.foodorderingapp.entity.OrderLineItem_OptionItem;

@Repository
public interface OrderLineItemOptionItemJpaRepository extends JpaRepository<OrderLineItem_OptionItem, Long>  {
    
}
