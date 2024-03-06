package com.foodapp.foodorderingapp.dto.order;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String dishId;
    private UUID userId;
    private Integer amount;


    public void setUserId(String userId) {
        this.userId = UUID.randomUUID();
    }

}
