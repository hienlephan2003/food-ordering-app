package com.foodapp.foodorderingapp.entity;
import lombok.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes")
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish that = (Dish) o;
        return id.equals(that.id) && category.equals(that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category);
    }

}

