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
    private Long dishId;
    List<CartItem_GroupOptionRequest> cartItemGroupOptionRequests;
    private int quantity;
}
