package com.foodapp.foodorderingapp.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartItemRequest {
    @JsonProperty("dish_id")
    private Long dishId;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("item_group_options")
    List<CartItem_GroupOptionRequest> cartItemGroupOptionRequests;
    private BigDecimal total;
    private int quantity;
}
