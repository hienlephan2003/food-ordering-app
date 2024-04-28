package com.foodapp.foodorderingapp.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodapp.foodorderingapp.enumeration.OrderStatus;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    @JsonProperty("address_id")
    private Long addressId;
    @JsonProperty("order_items")
    List<OrderLineItemRequest> orderItemRequests;
    private OrderStatus orderStatus;
}
