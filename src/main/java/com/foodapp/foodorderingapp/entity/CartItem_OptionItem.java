package com.foodapp.foodorderingapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item_options")
@Entity
public class CartItem_OptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ITEM_GROUP_OPTION_ID")
    @JsonBackReference
    private CartItem_GroupOption cartItem_groupOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPTION_ITEM_ID")
    private OptionItem optionItem;

    private int quantity;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItem_OptionItem that = (OrderLineItem_OptionItem) o;
        return id.equals(that.getId()) && optionItem.equals(that.getOptionItem()) && cartItem_groupOption.equals(cartItem_groupOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartItem_groupOption, optionItem);
    }
}