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
    private Long userId;
    List<CartItem_GroupOptionRequest> cartItemGroupOptionRequests;
    private BigDecimal total;
    private int quantity;
}
