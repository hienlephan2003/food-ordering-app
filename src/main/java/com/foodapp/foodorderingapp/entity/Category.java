package com.foodapp.foodorderingapp.entity;
import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Entity
public class Category {
    @Id
    private UUID id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    private String name;
    private Integer dishQuantity;
    
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
