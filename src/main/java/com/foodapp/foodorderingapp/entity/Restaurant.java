package com.foodapp.foodorderingapp.entity;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "restaurants")
@Entity
public class Restaurant {
    @Id
    private UUID id;
    private String imageUrl;
    private String coverImageUrl;
    private String name;
    private String mainDish;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Category> categories;
}
