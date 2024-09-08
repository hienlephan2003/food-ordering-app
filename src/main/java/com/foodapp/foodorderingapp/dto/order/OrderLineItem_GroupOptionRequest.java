package com.foodapp.foodorderingapp.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodapp.foodorderingapp.entity.OrderLineItem_OptionItem;
import lombok.*;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItem_GroupOptionRequest {
    private long groupOptionId;
    List<OrderLineItem_OptionItemRequest> orderLineItem_optionItems;
    private BigDecimal subTotalPrice;
}
