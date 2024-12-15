package com.foodapp.foodorderingapp.dto.product_discount;

import com.foodapp.foodorderingapp.enumeration.DiscountType;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDiscountRequest {
    private Long id;
    private Long restaurantId;
    private String name;
    private double discountValue;
    private DiscountType discountType;
    private Timestamp validFrom;
    private Timestamp validTo;
    private String couponCode;
    @Min(value = 0, message = "Maximum discount value should be greater than 0")
    private BigDecimal maximumDiscountValue;
    private String description;
    private String image;
    private int exchangeRate;
}
