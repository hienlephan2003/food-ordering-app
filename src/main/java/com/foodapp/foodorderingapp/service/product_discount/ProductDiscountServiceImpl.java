package com.foodapp.foodorderingapp.service.product_discount;

import com.foodapp.foodorderingapp.dto.product_discount.CreateProductDiscountRequest;
import com.foodapp.foodorderingapp.dto.product_discount.DiscountResponse;
import com.foodapp.foodorderingapp.dto.product_discount.UpdateProductDiscountRequest;
import com.foodapp.foodorderingapp.entity.ProductDiscount;
import com.foodapp.foodorderingapp.entity.Restaurant;
import com.foodapp.foodorderingapp.repository.ProductDiscountJpaRepository;
import com.foodapp.foodorderingapp.repository.RestaurantJpaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductDiscountServiceImpl implements ProductDiscountService{
    private final ProductDiscountJpaRepository productDiscountJpaRepository;
    private final ModelMapper mapper;
    private final RestaurantJpaRepository restaurantJpaRepository;


    @Override
    public DiscountResponse createProductDiscount(CreateProductDiscountRequest productDiscountRequest) {
        Restaurant restaurant = restaurantJpaRepository.findById(productDiscountRequest.getRestaurantId()).orElseThrow(() -> new RuntimeException("Dish not found"));
        ProductDiscount productDiscount = mapper.map(productDiscountRequest, ProductDiscount.class);
        productDiscount.setRestaurant(restaurant);
        return mapper.map( productDiscountJpaRepository.save(productDiscount), DiscountResponse.class);
    }
    @Override
    public DiscountResponse updateProductDiscount(UpdateProductDiscountRequest productDiscountRequest) {
        ProductDiscount discount = productDiscountJpaRepository.findById(productDiscountRequest.getId()).orElseThrow(
                () ->  new RuntimeException("Not found discount")
        );
        mapper.map(productDiscountRequest, discount);
        return mapper.map( productDiscountJpaRepository.save(discount), DiscountResponse.class);
    }

    @Override
    public DiscountResponse getProductDiscount(Long id) {
        return mapper.map( productDiscountJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Product discount not found")), DiscountResponse.class);
    }

    @Override
    public List<DiscountResponse> getProductDiscountByRestaurant(Long restaurantId) {
            return productDiscountJpaRepository.findByRestaurantId(restaurantId)
                    .stream()
                    .map(item ->mapper.map(item, DiscountResponse.class))
                    .collect(Collectors.toList());
        }

}
