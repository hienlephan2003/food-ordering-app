package com.foodapp.foodorderingapp.entity;




import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Table(name = "cart_item_group_options")
@Entity
@Builder
public class CartItem_GroupOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CART_ITEM_ID")
    @JsonBackReference
    private CartItem cartItem;

    @ManyToOne
    @JoinColumn(name = "GROUP_OPTION_ID")
    private GroupOption groupOption;

    @OneToMany(mappedBy = "cartIt" +
            "em_groupOption", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<CartItem_OptionItem> cartItemOptions;

    private BigDecimal groupOptionSubtotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem_GroupOption that = (CartItem_GroupOption) o;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
