package com.foodapp.foodorderingapp.dto.product_discount;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDiscountRequest {
    @JsonProperty("dish_id")
    private Long dishId;
    @JsonProperty("discount_value")
    private double discountValue;
    @JsonProperty("discount_unit")
    private int discountUnit;
    @JsonProperty("vaild_from")
    private Timestamp validFrom;
    @JsonProperty("valid_to")
    private Timestamp validTo;
    @JsonProperty("coupon_code")
    private String couponCode;
    @JsonProperty("minimum_order_value")
    @Min(value = 0, message = "Minimum order value should be greater than 0")
    private BigDecimal minimumOrderValue;
    @JsonProperty("maximum_discount_value")
    @Min(value = 0, message = "Maximum discount value should be greater than 0")
    private BigDecimal maximumDiscountValue;
    @JsonProperty("is_redeem_allowed")
    private boolean isRedeemAllowed;
}
