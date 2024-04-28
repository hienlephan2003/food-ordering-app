package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.product_discount.CreateProductDiscountRequest;
import com.foodapp.foodorderingapp.dto.product_discount.UpdateProductDiscountRequest;
import com.foodapp.foodorderingapp.entity.ProductDiscount;
import com.foodapp.foodorderingapp.service.productdiscount.ProductDiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class ProductDiscountController {
    private final ProductDiscountService productDiscountService;
    @PostMapping
    public ResponseEntity<ProductDiscount> createProductDiscount(@Valid @RequestBody CreateProductDiscountRequest productDiscountRequest) {
        return ResponseEntity.ok(productDiscountService.createProductDiscount(productDiscountRequest));
    }
    @PutMapping
    public ResponseEntity<ProductDiscount> updateProductDiscount(@Valid @RequestBody UpdateProductDiscountRequest productDiscountRequest){
        return ResponseEntity.ok(productDiscountService.updateProductDiscount(productDiscountRequest));
    }
}
