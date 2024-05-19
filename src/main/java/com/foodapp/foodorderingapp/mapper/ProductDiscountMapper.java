package com.foodapp.foodorderingapp.mapper;

import com.foodapp.foodorderingapp.dto.product_discount.CreateProductDiscountRequest;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.ProductDiscount;
import com.foodapp.foodorderingapp.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
@Component
public class ProductDiscountMapper {
    public ProductDiscount toProductDiscount(CreateProductDiscountRequest productDiscountRequest, Restaurant restaurant, Dish dish) {
        ProductDiscount productDiscount = ProductDiscount.builder()
                .dish(dish)
                .restaurant(restaurant)
                .name(productDiscountRequest.getName())
                .conditions(productDiscountRequest.getConditions())
                .discountUnit(productDiscountRequest.getDiscountUnit())
                .discountValue(productDiscountRequest.getDiscountValue())
                .createdTime(ZonedDateTime.now())
                .validFrom(productDiscountRequest.getValidFrom())
                .validTo(productDiscountRequest.getValidTo())
                .couponCode(productDiscountRequest.getCouponCode())
                .minimumOrderValue(productDiscountRequest.getMinimumOrderValue())
                .maximumDiscountValue(productDiscountRequest.getMaximumDiscountValue())
                .build();
        return productDiscount;
    }
}
