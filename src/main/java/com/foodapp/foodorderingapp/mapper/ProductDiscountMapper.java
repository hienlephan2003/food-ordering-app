package com.foodapp.foodorderingapp.mapper;

import com.foodapp.foodorderingapp.dto.product_discount.CreateProductDiscountRequest;
import com.foodapp.foodorderingapp.dto.product_discount.DiscountResponse;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.ProductDiscount;
import com.foodapp.foodorderingapp.entity.Restaurant;
import com.foodapp.foodorderingapp.enumeration.DiscountType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
@Component
public class ProductDiscountMapper {
    public ProductDiscount toProductDiscount(CreateProductDiscountRequest productDiscountRequest, Restaurant restaurant, Dish dish) {
        return ProductDiscount.builder()
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
    }
    public DiscountResponse toDiscountResponse(ProductDiscount productDiscount){
        return DiscountResponse.builder()
                .id(productDiscount.getId())
                .name(productDiscount.getName())
                .conditions(productDiscount.getConditions())
                .discountValue(productDiscount.getDiscountValue())
                .createdTime(productDiscount.getCreatedTime())
                .validFrom(productDiscount.getValidFrom())
                .validTo(productDiscount.getValidTo())
                .couponCode(productDiscount.getCouponCode())
                .minimumOrderValue(productDiscount.getMinimumOrderValue())
                .maximumDiscountValue(productDiscount.getMaximumDiscountValue())
                .discountType(productDiscount.getDiscountType())
                .maxUsed(productDiscount.getMaxUsed())
                .dishId(productDiscount.getDish().getId())
                .dish(DishMapper.toDishResponse(productDiscount.getDish()))
                .build();
    }
}
