package com.foodapp.foodorderingapp.service.cart;

import com.foodapp.foodorderingapp.dto.cart.*;
import com.foodapp.foodorderingapp.entity.*;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.repository.*;
import com.foodapp.foodorderingapp.repository.CartJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CartServiceIml implements CartService {
    private final OptionItemJpaRepository optionItemJpaRepository;
    private final GroupOptionJpaRepository groupOptionJpaRepository;
    private final DishJpaRepository dishJpaRepository;
    private final CartJpaRepository cartJpaRepository;
    private final CartOptionItemJpaRepository cartOptionItemJpaRepository;
    private final CartGroupOptionRepository cartGroupOptionRepository;
    private final UserJpaRepository userJpaRepository;

    private Pair<List<CartItem_OptionItem>, BigDecimal> getItemOptions(CartItem_GroupOptionRequest item_group,
            GroupOption groupOption) {
        AtomicReference<BigDecimal> subTotal = new AtomicReference<>(BigDecimal.ZERO);
        List<CartItem_OptionItem> item_options = item_group.getCartItem_optionItems().stream().map(item_option -> {
            OptionItem optionItem = optionItemJpaRepository.findById(item_option.getOptionItemId()).orElseThrow(
                    () -> new DataNotFoundException("Not found option item with " + item_option.getOptionItemId()));
            if (!Objects.equals(optionItem.getGroupOption().getId(), groupOption.getId())) {
                throw new IllegalArgumentException("Option item not valid" + item_option.getOptionItemId());
            }
            BigDecimal price = optionItem.getPrice().multiply(BigDecimal.valueOf(item_option.getQuantity()));
            subTotal.updateAndGet(v -> v.add(price));
            return CartItem_OptionItem.builder()
                    .optionItem(optionItem)
                    .quantity(item_option.getQuantity())
                    .price(price)
                    .build();
        }).toList();

        return Pair.of(item_options, subTotal.get());
    }

    private Pair<List<CartItem_GroupOption>, BigDecimal> getItemGroups(CartItemRequest itemRequest, CartItem item) {
        AtomicReference<BigDecimal> itemOptionPrice = new AtomicReference<>(BigDecimal.ZERO);
        List<CartItem_GroupOption> item_groups = itemRequest.getCartItemGroupOptionRequests().stream()
                .map(item_group -> {
                    GroupOption groupOption = groupOptionJpaRepository.findById(item_group.getGroupOptionId())
                            .orElseThrow(() -> new DataNotFoundException(
                                    "Not found group option with " + item_group.getGroupOptionId()));
                    Pair<List<CartItem_OptionItem>, BigDecimal> res = getItemOptions(item_group, groupOption);
                    System.out.println(res.getSecond());
                    itemOptionPrice.updateAndGet(x -> x.add(res.getSecond()));

                    CartItem_GroupOption cartItemGroupOption = CartItem_GroupOption.builder()
                            .groupOption(groupOption)
                            .cartItemOptions(res.getFirst())
                            .groupOptionSubtotal(res.getSecond())
                            .cartItem(item)
                            .build();
                    CartItem_GroupOption binh = cartGroupOptionRepository.save(cartItemGroupOption);
                    res.getFirst().stream().forEach(option_item -> {
                        option_item.setCartItem_groupOption(binh);
                        cartOptionItemJpaRepository.save(option_item);
                    });

                    return CartItem_GroupOption.builder()
                            .groupOption(groupOption)
                            .cartItemOptions(res.getFirst())
                            .groupOptionSubtotal(res.getSecond())
                            .build();
                }).toList();
        System.out.println(itemOptionPrice.get());
        return Pair.of(item_groups, itemOptionPrice.get());
    }

    @Override
    public CartItem addItemToCart(CartItemRequest itemRequest) {
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        Dish dish = dishJpaRepository.findById(itemRequest.getDishId())
                .orElseThrow(() -> new DataNotFoundException("Not found dish with id " + itemRequest.getDishId()));
        User user = userJpaRepository.findById(itemRequest.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Not found user with id " + itemRequest.getUserId()));
        CartItem item = CartItem
                .builder()
                .dish(dish)
                .user(user)
                .total(BigDecimal.ZERO)
                .quantity(itemRequest.getQuantity())
                .build();
        CartItem cartItem = cartJpaRepository.save(item);
        Pair<List<CartItem_GroupOption>, BigDecimal> res = getItemGroups(itemRequest, cartItem);
        BigDecimal subTotal = (res.getSecond().add(dish.getPrice()))
                .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
        totalPrice.updateAndGet(x -> x.add(subTotal));
        cartItem.setTotal(totalPrice.get());

        return cartJpaRepository.save(cartItem);
    }

    @Override
    @Transactional
    public boolean removeFromCart(long cartId) {
        try {
            cartJpaRepository.deleteById(cartId);
            return true;
        } catch (Exception e) {
            throw new DataNotFoundException("Not found cart by id " + cartId);
        }
    }

    @Override
    public List<CartItem> getCartsByDish(CartOfDishRequest request) {
        List<CartItem> cartItems = cartJpaRepository.findByDishAndUser(request.getDishId(), request.getUserId());
        return cartItems;
    }

    @Override
    public List<CartItem> getCartByUser(Long userId) {
        return cartJpaRepository.findByUser(userId);
    }

    @Override
    public List<CartItem> getCartByRestaurant(MyCartRestaurantRequest request) {
        return cartJpaRepository.findByRestaurant(request.getUserId(), request.getRestaurantId());
    }

    @Override
    public CartItem updateCart(int quantity, long id) {
        CartItem cartItem = cartJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found cart"));
        int preQuantity = cartItem.getQuantity();
        BigDecimal total = cartItem.getTotal();
        cartItem.setQuantity(quantity);
        if(quantity > 0) {
            cartItem.setTotal(total.multiply(BigDecimal.valueOf(quantity)).divide(BigDecimal.valueOf(preQuantity)));

        }
        else {
            cartJpaRepository.deleteById(id);
            return null;
        }
        return cartJpaRepository.save(cartItem);
    }

    @Override
    public List<RestaurantCartResponse> getRestaurantOfCart(long userId) {
        List<CartItem> carts = cartJpaRepository.findByUser(userId);
        List<RestaurantCartResponse> restaurantCarts = new ArrayList<>();
        List<Long> listRestaurantIds = new ArrayList<>();
        carts.stream().forEach((cart -> {
            Long restaurantId = cart.getDish().getRestaurant().getId();
            RestaurantCartResponse data = new RestaurantCartResponse(cart.getDish().getRestaurant(), cart.getTotal(),
                    cart.getQuantity());
            if (listRestaurantIds.contains(restaurantId)) {
                RestaurantCartResponse res = restaurantCarts.stream()
                        .filter(item -> item.getRestaurant().getId().equals(restaurantId)).findFirst().orElseThrow();
                restaurantCarts.removeIf((item) -> item.getRestaurant().getId().equals(restaurantId));
                data.setQuantity(data.getQuantity() + res.getQuantity());
                data.setTotalPrice(data.getTotalPrice().add(res.getTotalPrice()));
                restaurantCarts.add(data);
            } else {
                listRestaurantIds.add(restaurantId);
                restaurantCarts.add(data);
            }
        }));
        return restaurantCarts;
    }

}
