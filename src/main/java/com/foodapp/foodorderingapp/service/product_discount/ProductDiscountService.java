package com.foodapp.foodorderingapp.service.product_discount;

import com.foodapp.foodorderingapp.dto.product_discount.CreateProductDiscountRequest;
import com.foodapp.foodorderingapp.dto.product_discount.UpdateProductDiscountRequest;
import com.foodapp.foodorderingapp.entity.ProductDiscount;

import java.util.List;

public interface ProductDiscountService {
    ProductDiscount createProductDiscount(CreateProductDiscountRequest productDiscountRequest);
    ProductDiscount updateProductDiscount(UpdateProductDiscountRequest productDiscountRequest);
    ProductDiscount getProductDiscount(Long id);
    List<ProductDiscount> getProductDiscountByRestaurant(Long restaurantId);
}
