package com.foodapp.foodorderingapp.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem_OptionItemRequest {
    private long optionItemId;
    private int quantity;
}
