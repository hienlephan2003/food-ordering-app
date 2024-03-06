package com.foodapp.foodorderingapp.entity;

import lombok.*;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group-options")
@Entity
public class GroupOption {
    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "groupOption", cascade = CascadeType.ALL)
    private List<OptionItem> optionItems;
    
    private String name;
}
