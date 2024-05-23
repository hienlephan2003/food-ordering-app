package com.foodapp.foodorderingapp.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String menuImageUrl;
    private String photoUrls;
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
    @ManyToOne(cascade = CascadeType.ALL)
    private Address addressEntity;
    private String description;
    private String address ;
    private String  latitude ;
    private String longitude ;
    private String locationId ;
    private Integer numReviews;
    private String rating;
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
