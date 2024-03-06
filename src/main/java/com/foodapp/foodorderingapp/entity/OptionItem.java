package com.foodapp.foodorderingapp.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group-option-items")
@Entity
public class OptionItem {
    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "GROUP_OPTION_ID")
    private GroupOption groupOption;
    
    private String name;
    private BigDecimal price;
}
