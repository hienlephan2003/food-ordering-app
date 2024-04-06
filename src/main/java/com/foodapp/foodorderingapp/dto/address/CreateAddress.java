package com.foodapp.foodorderingapp.dto.address;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAddress {
    private long userId;
    private String address;
}
