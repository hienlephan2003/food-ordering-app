package com.foodapp.foodorderingapp.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderLineItemRequest {
    @JsonProperty("dish_id")
    private Long dishId;
    @JsonProperty("item_group_options")
    List<OrderLineItem_GroupOptionRequest> orderLineItemGroupOptionRequests;
    private int quantity;
}
