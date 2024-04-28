package com.foodapp.foodorderingapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_discounts")
@Entity
@Getter
@Builder
public class ProductDiscount {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;
    private double discountValue;
    private int discountUnit;
    private ZonedDateTime createdTime;
    private Timestamp validFrom;
    private Timestamp validTo;
    private String couponCode;
    private BigDecimal minimumOrderValue;
    private BigDecimal maximumDiscountValue;
    private boolean isRedeemAllowed;
}