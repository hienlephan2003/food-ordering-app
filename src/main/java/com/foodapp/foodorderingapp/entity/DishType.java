package com.foodapp.foodorderingapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table(name = "dish_types")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DishType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
}
