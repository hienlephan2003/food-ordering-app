package com.foodapp.foodorderingapp.entity;
import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes")
@Entity
public class Dish {
    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    
    private String imageUrl;
    private String name;
    private String description;
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
