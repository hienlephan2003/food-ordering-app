package com.foodapp.foodorderingapp.dto.order;

import lombok.*;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String dishId;
    private Long userId;
    private Integer amount;
}
