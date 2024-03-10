package com.foodapp.foodorderingapp.entity;

import lombok.*;

import java.math.BigDecimal;


import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group-option-items")
@Entity
public class GroupOptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_OPTION_ID")
    private GroupOption groupOption;
    
    private String name;
    private BigDecimal price;
    private String imageUrl;
}
