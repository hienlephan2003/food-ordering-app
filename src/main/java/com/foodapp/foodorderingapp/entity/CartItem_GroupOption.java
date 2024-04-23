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
    @JsonBackReference
    private GroupOption groupOption;

    @OneToMany(mappedBy = "cartItem_groupOption", fetch = FetchType.LAZY)
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
