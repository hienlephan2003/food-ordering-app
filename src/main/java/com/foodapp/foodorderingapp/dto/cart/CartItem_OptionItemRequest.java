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
    @JsonProperty("option_item_id")
    private long optionItemId;
    private int quantity;
}
