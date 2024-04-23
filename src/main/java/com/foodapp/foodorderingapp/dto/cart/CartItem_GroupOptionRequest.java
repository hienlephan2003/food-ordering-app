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
public class CartItem_GroupOptionRequest {
    @JsonProperty("group_option_id")
    private long groupOptionId;
    @JsonProperty("item_options")
    List<CartItem_OptionItemRequest> cartItem_optionItems;
    @JsonProperty("sub_option_price")
    private BigDecimal subOptionPrice;
}
