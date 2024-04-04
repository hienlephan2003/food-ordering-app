package com.foodapp.foodorderingapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "dish_types")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Setter
@Getter
public class DishType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "dishType")
    private List<Dish> dishes;
}
