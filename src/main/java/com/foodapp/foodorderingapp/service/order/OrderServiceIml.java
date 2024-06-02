package com.foodapp.foodorderingapp.service.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.foodapp.foodorderingapp.dto.order.OrderLineItemRequest;
import com.foodapp.foodorderingapp.dto.order.OrderLineItem_GroupOptionRequest;
import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.dto.order.OrderResponse;
import com.foodapp.foodorderingapp.entity.*;
import com.foodapp.foodorderingapp.enumeration.DeliveryStatus;
import com.foodapp.foodorderingapp.enumeration.OrderStatus;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.mapper.OrderMapper;
import com.foodapp.foodorderingapp.repository.*;
import com.foodapp.foodorderingapp.security.UserPrinciple;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceIml implements OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final OptionItemJpaRepository optionItemJpaRepository;
    private final GroupOptionJpaRepository groupOptionJpaRepository;
    private final DishJpaRepository dishJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final OrderLineItemGroupOptionJpaRepository orderLineItemGroupOptionJpaRepository;
    private final OrderLineItemOptionItemJpaRepository orderLineItemOptionItemJpaRepository;
    private final OrderLineItemJpaRepository orderLineItemJpaRepository;
    private final AddressJpaRepository addressJpaRepository;
    private Pair<List<OrderLineItem_OptionItem>, BigDecimal> getItemOptions(OrderLineItem_GroupOptionRequest item_group,
            GroupOption groupOption) {
        AtomicReference<BigDecimal> subTotal = new AtomicReference<>(BigDecimal.ZERO);
        List<OrderLineItem_OptionItem> item_options = item_group.getOrderLineItem_optionItems().stream()
                .map(item_option -> {
                    OptionItem optionItem = optionItemJpaRepository.findById(item_option.getOptionItemId()).orElseThrow(
                            () -> new DataNotFoundException(
                                    "Not found option item with " + item_option.getOptionItemId()));
                    if (!Objects.equals(optionItem.getGroupOption().getId(), groupOption.getId())) {
                        throw new IllegalArgumentException("Option item not valid" + item_option.getOptionItemId());
                    }
                    BigDecimal price = optionItem.getPrice().multiply(BigDecimal.valueOf(item_option.getQuantity()));
                    subTotal.updateAndGet(v -> v.add(price));
                    return OrderLineItem_OptionItem.builder()
                            .optionItem(optionItem)
                            .quantity(item_option.getQuantity())
                            .price(price)
                            .build();
                }).toList();
        return Pair.of(item_options, subTotal.get());
    }

    private Pair<List<OrderLineItem_GroupOption>, BigDecimal> getItemGroups(OrderLineItemRequest itemRequest,
            OrderLineItem item) {
        AtomicReference<BigDecimal> itemOptionPrice = new AtomicReference<>(BigDecimal.ZERO);
        List<OrderLineItem_GroupOption> item_groups = itemRequest.getOrderLineItemGroupOptionRequests().stream()
                .map(item_group -> {
                    GroupOption groupOption = groupOptionJpaRepository.findById(item_group.getGroupOptionId())
                            .orElseThrow(() -> new DataNotFoundException(
                                    "Not found group option with " + item_group.getGroupOptionId()));
                    Pair<List<OrderLineItem_OptionItem>, BigDecimal> res = getItemOptions(item_group, groupOption);
                    itemOptionPrice.updateAndGet(x -> x.add(res.getSecond()));
                    OrderLineItem_GroupOption orderLineItem_GroupOption = OrderLineItem_GroupOption.builder()
                            .groupOption(groupOption)
                            .orderLineItemOptions(res.getFirst())
                            .groupOptionSubtotal(res.getSecond())
                            .orderLineItem(item)
                            .build();
                    OrderLineItem_GroupOption orderLineItem_GroupOptionData = orderLineItemGroupOptionJpaRepository
                            .save(orderLineItem_GroupOption);
                    res.getFirst().stream().forEach(option_item -> {
                        option_item.setOrderLineItem_GroupOption(orderLineItem_GroupOptionData);
                        orderLineItemOptionItemJpaRepository.save(option_item);
                    });

                    return OrderLineItem_GroupOption.builder()
                            .groupOption(groupOption)
                            .orderLineItemOptions(res.getFirst())
                            .groupOptionSubtotal(res.getSecond())
                            .build();
                }).toList();
        return Pair.of(item_groups, itemOptionPrice.get());
    }
    @Transactional
    @Override
    public Order createNewOrder(OrderRequest orderRequest) {
        UserPrinciple currentUser = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Order order = Order.builder()
                .user(userJpaRepository.findById(currentUser.getUserId())
                        .orElseThrow(() -> new DataNotFoundException("Not found user")))
                .orderStatus(orderRequest.getOrderStatus())
                .deliveryStatus(DeliveryStatus.PENDING)
                .address(orderRequest.getAddress())
                .build();

        Order orderData = orderJpaRepository.save(order);
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        List<OrderLineItem> orderLineItems = orderRequest.getOrderItemRequests().stream().map(itemRequest -> {
            Dish dish = dishJpaRepository.findById(itemRequest.getDishId())
                    .orElseThrow(() -> new DataNotFoundException("Not found dish with id " + itemRequest.getDishId()));
            OrderLineItem orderLineItem = OrderLineItem
                    .builder()
                    .dish(dish)
                    .quantity(itemRequest.getQuantity())
                    .order(orderData)
                    .build();
            OrderLineItem item = orderLineItemJpaRepository.save(orderLineItem);
            Pair<List<OrderLineItem_GroupOption>, BigDecimal> res = getItemGroups(itemRequest, item);
            // orderLineItem.setOrderLineItemGroupOptions(res.getFirst());
            BigDecimal subTotal = (res.getSecond())
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice.updateAndGet(x -> x.add(subTotal).add(dish.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()))));
            item.setSubTotal(subTotal);
            return orderLineItem;
        }).toList();   
        
        if (orderRequest.getTotalPrice().compareTo(totalPrice.get()) != 0 ) {
            throw new IllegalArgumentException("total_price field is not an accurate value" + orderRequest.getTotalPrice().toString() + ",," + totalPrice.get());
        } else
            orderData.setPrice(totalPrice.get());
        return orderJpaRepository.save(orderData);
    }

    @Override
    public Optional<Order> findById(String orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<Order> findByTrackingId(String trackingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTrackingId'");
    }

    @Override
    public List<Order> getByUser(Long userId) {
            return orderJpaRepository.findByUser(userId);
    }

    @Override
    public List<OrderResponse> getByRestaurantAndOrderStatus (Long restaurantId, OrderStatus orderStatus) {
            return orderJpaRepository.findOrdersByRestaurantIdAndOrderStatus(restaurantId, orderStatus).stream().map(OrderMapper::toOrderResponse).toList();
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderJpaRepository.findById(orderId).orElseThrow(()-> new DataNotFoundException("Not found order"));
        order.setOrderStatus(orderStatus);
        return orderJpaRepository.save(order);
    }
}
