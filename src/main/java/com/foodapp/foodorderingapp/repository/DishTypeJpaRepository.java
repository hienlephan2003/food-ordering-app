package com.foodapp.foodorderingapp.repository;

import com.foodapp.foodorderingapp.entity.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DishTypeJpaRepository extends JpaRepository<DishType, Long> {
}
