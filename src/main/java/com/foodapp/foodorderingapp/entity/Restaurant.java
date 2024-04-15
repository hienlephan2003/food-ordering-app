package com.foodapp.foodorderingapp.entity;
import com.foodapp.foodorderingapp.enumeration.RestaurantStatus;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "restaurants")
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String coverImageUrl;
    private String name;
    private String mainDish;
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Category> categories;
    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private User owner;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
