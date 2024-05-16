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
@Table(name = "order_line_item_options")
@Entity
public class OrderLineItem_OptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_LINE_ITEM_GROUP_OPTION_ID")
    @JsonBackReference
    private OrderLineItem_GroupOption orderLineItem_GroupOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_OPTION_ITEM_ID")
    @JsonBackReference
    private OptionItem optionItem;
    
    private int quantity;
    private BigDecimal price;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItem_OptionItem that = (OrderLineItem_OptionItem) o;
        return id.equals(that.id) && optionItem.equals(that.optionItem) && orderLineItem_GroupOption.equals(orderLineItem_GroupOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderLineItem_GroupOption, optionItem);
    }
}