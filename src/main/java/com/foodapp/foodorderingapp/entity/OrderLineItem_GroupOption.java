package com.foodapp.foodorderingapp.entity;




import com.fasterxml.jackson.annotation.JsonBackReference;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_line_item_group_options")
@Entity
@Builder
public class OrderLineItem_GroupOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_LINE_ITEM_ID")
    @JsonBackReference
    private OrderLineItem orderLineItem;

    @ManyToOne
    @JoinColumn(name = "GROUP_OPTION_ID")
    @JsonBackReference
    private GroupOption groupOption;

    @OneToMany(mappedBy = "orderLineItem_GroupOption", fetch = FetchType.LAZY)
    List<OrderLineItem_OptionItem> orderLineItemOptions;

    private BigDecimal groupOptionSubtotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItem_GroupOption that = (OrderLineItem_GroupOption) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
