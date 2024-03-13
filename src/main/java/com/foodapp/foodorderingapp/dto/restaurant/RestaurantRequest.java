package com.foodapp.foodorderingapp.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RestaurantRequest {
    @JsonProperty("restaurant_id")
    private long restaurantId;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("cover_image_url")
    private String coverImageUrl;
    @NotEmpty
    private String name;
    @JsonProperty("main_dish")
    private String mainDish;
    @JsonProperty("owner_id")
    private long ownerId;
}
