package com.foodapp.foodorderingapp.service.productdiscount;

import com.foodapp.foodorderingapp.dto.product_discount.CreateProductDiscountRequest;
import com.foodapp.foodorderingapp.dto.product_discount.UpdateProductDiscountRequest;
import com.foodapp.foodorderingapp.entity.ProductDiscount;
import org.hibernate.sql.Update;

public interface ProductDiscountService {
    ProductDiscount createProductDiscount(CreateProductDiscountRequest productDiscountRequest);
    ProductDiscount updateProductDiscount(UpdateProductDiscountRequest productDiscountRequest);
}
