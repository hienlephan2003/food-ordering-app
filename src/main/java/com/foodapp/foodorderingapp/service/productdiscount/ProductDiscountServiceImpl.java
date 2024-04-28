package com.foodapp.foodorderingapp.service.productdiscount;

import com.foodapp.foodorderingapp.dto.product_discount.CreateProductDiscountRequest;
import com.foodapp.foodorderingapp.dto.product_discount.UpdateProductDiscountRequest;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.ProductDiscount;
import com.foodapp.foodorderingapp.helper.DishHelper;
import com.foodapp.foodorderingapp.mapper.ProductDiscountMapper;
import com.foodapp.foodorderingapp.repository.DishJpaRepository;
import com.foodapp.foodorderingapp.repository.ProductDiscountJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductDiscountServiceImpl implements ProductDiscountService{
    private final DishHelper dishHelper;
    private final ProductDiscountJpaRepository productDiscountJpaRepository;
    private final ProductDiscountMapper productDiscountMapper;

    public ProductDiscountServiceImpl(DishHelper dishHelper, ProductDiscountJpaRepository productDiscountJpaRepository, ProductDiscountMapper productDiscountMapper) {
        this.dishHelper = dishHelper;
        this.productDiscountJpaRepository = productDiscountJpaRepository;
        this.productDiscountMapper = productDiscountMapper;
    }

    @Override
    public ProductDiscount createProductDiscount(CreateProductDiscountRequest productDiscountRequest) {
        Dish dish = dishHelper.findDishById(productDiscountRequest.getDishId()).orElseThrow(() -> new RuntimeException("Dish not found"));
        ProductDiscount productDiscount = productDiscountMapper.toProductDiscount(productDiscountRequest);
        productDiscount.setDish(dish);
        return productDiscountJpaRepository.save(productDiscount);
    }
    @Override
    public ProductDiscount updateProductDiscount(UpdateProductDiscountRequest productDiscountRequest) {
        return null;
    }
}
