package com.foodapp.foodorderingapp.service.cart;

import com.foodapp.foodorderingapp.dto.cart.CartItemRequest;
import com.foodapp.foodorderingapp.dto.cart.CartItem_GroupOptionRequest;
import com.foodapp.foodorderingapp.entity.*;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.repository.*;
import com.foodapp.foodorderingapp.repository.CartJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CartServiceIml implements CartService {
    private final OptionItemJpaRepository optionItemJpaRepository;
    private final GroupOptionJpaRepository groupOptionJpaRepository;
    private final DishJpaRepository dishJpaRepository;
    private final CartJpaRepository cartJpaRepository;
    private Pair<List<CartItem_OptionItem>, BigDecimal> getItemOptions(CartItem_GroupOptionRequest item_group, GroupOption groupOption){
        AtomicReference<BigDecimal> subTotal = new AtomicReference<>(BigDecimal.ZERO);
       List<CartItem_OptionItem> item_options = item_group.getCartItem_optionItems().stream().map(item_option ->{
            OptionItem optionItem = optionItemJpaRepository.findById(item_option.getOptionItemId()).orElseThrow(
                    () ->   new DataNotFoundException("Not found option item with " + item_option.getOptionItemId())
            );
            if(!Objects.equals(optionItem.getGroupOption().getId(), groupOption.getId())){
                throw new IllegalArgumentException("Option item not valid" + item_option.getOptionItemId());
            }
            BigDecimal price = optionItem.getPrice().multiply(BigDecimal.valueOf(item_option.getQuantity()));
            subTotal.updateAndGet(v -> v.add(price));
            return  CartItem_OptionItem.builder()
                    .optionItem(optionItem)
                    .quantity(item_option.getQuantity())
                    .price(price)
                    .build();
        }).toList();
       return Pair.of(item_options, subTotal.get());
    }
    private Pair<List<CartItem_GroupOption>, BigDecimal> getItemGroups(CartItemRequest itemRequest, CartItem item){
        AtomicReference<BigDecimal> itemOptionPrice = new AtomicReference<>(BigDecimal.ZERO);
        List<CartItem_GroupOption> item_groups = itemRequest.getCartItemGroupOptionRequests().stream().map(item_group -> {
            GroupOption groupOption = groupOptionJpaRepository.findById(item_group.getGroupOptionId()).orElseThrow(() -> new DataNotFoundException(
                    "Not found group option with " + item_group.getGroupOptionId()
            ));
            Pair<List<CartItem_OptionItem>, BigDecimal> res = getItemOptions(item_group, groupOption);
            itemOptionPrice.updateAndGet(x -> x.add(res.getSecond()));
            return CartItem_GroupOption.builder()
                    .groupOption(groupOption)
                    .cartItemOptions(res.getFirst())
                    .groupOptionSubtotal(res.getSecond())
                    .build();
        }).toList();
        return Pair.of(item_groups, itemOptionPrice.get());
    }
    @Override
    public CartItem addItemToCart(CartItemRequest itemRequest) {
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        Dish dish = dishJpaRepository.findById(itemRequest.getDishId())
                .orElseThrow(() -> new DataNotFoundException("Not found dish with id " + itemRequest.getDishId()));
        CartItem item =  CartItem
                .builder()
                .dish(dish)
                .quantity(itemRequest.getQuantity())
                .build();
        Pair<List<CartItem_GroupOption>, BigDecimal> res = getItemGroups(itemRequest ,item);
        item.setCartItem_groupOptions(res.getFirst());
        BigDecimal subTotal = res.getSecond().add(dish.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        totalPrice.updateAndGet(x -> x.add(subTotal));
        item.setTotal(subTotal);
        return item;
    }

    @Override
    @Transactional
    public boolean removeFromCart(long cartId) {
        try {
            cartJpaRepository.deleteById(cartId);
            return true;
        }
        catch(Exception e){
            throw new DataNotFoundException("Not found cart by id " + cartId);
        }
    }
}
