package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDiscountJpaRepository extends JpaRepository<ProductDiscount, Long> {
}
