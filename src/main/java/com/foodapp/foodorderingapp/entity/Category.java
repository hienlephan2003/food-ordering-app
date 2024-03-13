package com.foodapp.foodorderingapp.entity;
import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Dish> dishes;

    private String name;
    private Integer dishQuantity;
    private boolean isActive;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category that = (Category) o;
        return id.equals(that.id) && restaurant.equals(that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurant);
    }
}
