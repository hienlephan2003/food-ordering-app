package com.foodapp.foodorderingapp.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodapp.foodorderingapp.enumeration.DishStatus;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.boot.model.internal.XMLContext;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.util.List;
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

    @ManyToOne()
    @JoinColumn(name = "RESTAURANT_ID")
//    @JsonIgnoreProperties("categories")
    private Restaurant restaurant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    @JsonBackReference
    private Category category;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private DishStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_type_id")
    private DishType dishType;
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

