package com.foodapp.foodorderingapp.entity;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data 
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cart_items")
public class CartItem {
    @Id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DISH_ID")
    private Dish dish;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cartItem")
    private List<CartItem_GroupOption> cartItem_groupOptions;

    private Integer quantity;
    private BigDecimal total;
}
