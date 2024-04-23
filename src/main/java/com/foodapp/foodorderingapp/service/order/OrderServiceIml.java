package com.foodapp.foodorderingapp.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


import com.foodapp.foodorderingapp.dto.order.OrderLineItemRequest;
import com.foodapp.foodorderingapp.dto.order.OrderLineItem_GroupOptionRequest;
import com.foodapp.foodorderingapp.dto.order.OrderRequest;
import com.foodapp.foodorderingapp.entity.*;
import com.foodapp.foodorderingapp.enumeration.OrderStatus;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.repository.*;
import com.foodapp.foodorderingapp.security.UserPrinciple;
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
    private Pair<List<OrderLineItem_OptionItem>, BigDecimal> getItemOptions(OrderLineItem_GroupOptionRequest item_group, GroupOption groupOption){
        AtomicReference<BigDecimal> subTotal = new AtomicReference<>(BigDecimal.ZERO);
       List<OrderLineItem_OptionItem> item_options = item_group.getOrderLineItem_optionItems().stream().map(item_option ->{
            OptionItem optionItem = optionItemJpaRepository.findById(item_option.getOptionItemId()).orElseThrow(
                    () ->   new DataNotFoundException("Not found option item with " + item_option.getOptionItemId())
            );
            if(!Objects.equals(optionItem.getGroupOption().getId(), groupOption.getId())){
                throw new IllegalArgumentException("Option item not valid" + item_option.getOptionItemId());
            }
            BigDecimal price = optionItem.getPrice().multiply(BigDecimal.valueOf(item_option.getQuantity()));
            subTotal.updateAndGet(v -> v.add(price));
            return  OrderLineItem_OptionItem.builder()
                    .optionItem(optionItem)
                    .quantity(item_option.getQuantity())
                    .price(price)
                    .build();
        }).toList();
       return Pair.of(item_options, subTotal.get());
    }
    private Pair<List<OrderLineItem_GroupOption>, BigDecimal> getItemGroups(OrderLineItemRequest itemRequest, OrderLineItem item){
        AtomicReference<BigDecimal> itemOptionPrice = new AtomicReference<>(BigDecimal.ZERO);
        List<OrderLineItem_GroupOption> item_groups = itemRequest.getOrderLineItemGroupOptionRequests().stream().map(item_group -> {
            GroupOption groupOption = groupOptionJpaRepository.findById(item_group.getGroupOptionId()).orElseThrow(() -> new DataNotFoundException(
                    "Not found group option with " + item_group.getGroupOptionId()
            ));
            Pair<List<OrderLineItem_OptionItem>, BigDecimal> res = getItemOptions(item_group, groupOption);
            itemOptionPrice.updateAndGet(x -> x.add(res.getSecond()));
            return OrderLineItem_GroupOption.builder()
                    .groupOption(groupOption)
                    .orderLineItemOptions(res.getFirst())
                    .groupOptionSubtotal(res.getSecond())
                    .build();
        }).toList();
        return Pair.of(item_groups, itemOptionPrice.get());
    }
    @Override
    public Order createNewOrder(OrderRequest orderRequest) {
        UserPrinciple currentUser = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = Order.builder()
                .user(userJpaRepository.findById(currentUser.getUserId()).orElseThrow(() ->new DataNotFoundException("Not found user")))
                .orderStatus(OrderStatus.PENDING)
                .build();
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        List<OrderLineItem> orderLineItems = orderRequest.getOrderItemRequests().stream().map(itemRequest -> {
            Dish dish = dishJpaRepository.findById(itemRequest.getDishId())
                    .orElseThrow(() -> new DataNotFoundException("Not found dish with id " + itemRequest.getDishId()));
            OrderLineItem orderLineItem =  OrderLineItem
                    .builder()
                    .dish(dish)
                    .quantity(itemRequest.getQuantity())
                    .order(order)
                    .build();
            Pair<List<OrderLineItem_GroupOption>, BigDecimal> res = getItemGroups(itemRequest ,orderLineItem);
            orderLineItem.setOrderLineItemGroupOptions(res.getFirst());
            BigDecimal subTotal = res.getSecond().add(dish.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            totalPrice.updateAndGet(x -> x.add(subTotal));
            orderLineItem.setSubTotal(subTotal);
           return orderLineItem;
        }).toList();
        order.setItems(orderLineItems);
        if(!Objects.equals(orderRequest.getTotalPrice(), totalPrice.get())){
            throw new IllegalArgumentException("total_price field is not an accurate value");
        }
        else order.setPrice(totalPrice.get());
        return order;
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
}
