package com.foodapp.foodorderingapp.entity;




import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_line_item_group_options")
@Entity
public class OrderLineItemGroupOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_LINE_ITEM_ID")
    private OrderLineItem orderLineItem;

    @ManyToOne
    @JoinColumn(name = "GROUP_OPTION_ID")
    private GroupOption groupOption;

    @OneToMany(mappedBy = "orderLineItemGroupOption", fetch = FetchType.LAZY)
    List<OrderLineItemOption> orderLineItemOptions;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItemGroupOption that = (OrderLineItemGroupOption) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
