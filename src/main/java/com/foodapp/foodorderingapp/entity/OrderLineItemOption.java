package com.foodapp.foodorderingapp.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_line_item_options")
@Entity
public class OrderLineItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_LINE_ITEM_GROUP_OPTION_ID")
    private OrderLineItemGroupOption orderLineItemGroupOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_OPTION_ITEM_ID")
    private GroupOptionItem groupOptionItem;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItemOption that = (OrderLineItemOption) o;
        return id.equals(that.id) && groupOptionItem.equals(that.groupOptionItem) && orderLineItemGroupOption.equals(orderLineItemGroupOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderLineItemGroupOption, groupOptionItem);
    }
}