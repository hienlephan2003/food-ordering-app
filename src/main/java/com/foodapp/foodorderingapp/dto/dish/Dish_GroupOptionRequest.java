package com.foodapp.foodorderingapp.dto.dish;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dish_GroupOptionRequest {
    @JsonProperty("dish_id")
    private long dishId;
    @JsonProperty("group_option_id")
    private long groupOptionId;
}
