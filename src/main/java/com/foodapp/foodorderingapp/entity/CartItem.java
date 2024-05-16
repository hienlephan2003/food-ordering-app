package com.foodapp.foodorderingapp.entity;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DISH_ID")
    private Dish dish;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cartItem", cascade = CascadeType.ALL)
    private List<CartItem_GroupOption> cartItem_groupOptions;

    private Integer quantity;
    private BigDecimal total;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP) // Specify the temporal type if using java.util.Date
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
}
