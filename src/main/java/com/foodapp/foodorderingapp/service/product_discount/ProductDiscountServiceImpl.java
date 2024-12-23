package com.foodapp.foodorderingapp.service.product_discount;

import com.foodapp.foodorderingapp.dto.product_discount.CreateProductDiscountRequest;
import com.foodapp.foodorderingapp.dto.product_discount.DiscountResponse;
import com.foodapp.foodorderingapp.dto.product_discount.UpdateProductDiscountRequest;
import com.foodapp.foodorderingapp.entity.Dish;
import com.foodapp.foodorderingapp.entity.ProductDiscount;
import com.foodapp.foodorderingapp.entity.Restaurant;
import com.foodapp.foodorderingapp.helper.DishHelper;
import com.foodapp.foodorderingapp.mapper.ProductDiscountMapper;
import com.foodapp.foodorderingapp.repository.ProductDiscountJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public DiscountResponse createProductDiscount(CreateProductDiscountRequest productDiscountRequest) {
        Dish dish = dishHelper.findDishById(productDiscountRequest.getDishId()).orElseThrow(() -> new RuntimeException("Dish not found"));
        Restaurant restaurant = dish.getRestaurant();
        ProductDiscount productDiscount = productDiscountMapper.toProductDiscount(productDiscountRequest, restaurant, dish);
        productDiscount.setDish(dish);
        return productDiscountMapper.toDiscountResponse( productDiscountJpaRepository.save(productDiscount));
    }
    @Override
    public DiscountResponse updateProductDiscount(UpdateProductDiscountRequest productDiscountRequest) {
        return null;
    }

    @Override
    public DiscountResponse getProductDiscount(Long id) {
        return productDiscountMapper.toDiscountResponse( productDiscountJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Product discount not found")));
    }

    @Override
    public List<DiscountResponse> getProductDiscountByRestaurant(Long restaurantId) {
            return productDiscountJpaRepository.findByRestaurantId(restaurantId)
                    .stream()
                    .map(productDiscountMapper::toDiscountResponse)
                    .collect(Collectors.toList());
        }

}
