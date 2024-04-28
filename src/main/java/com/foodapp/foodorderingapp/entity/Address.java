package com.foodapp.foodorderingapp.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;

@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Setter
@Getter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "addresses", fetch = FetchType.LAZY)
    Set<User> users = new HashSet<>();
    private String address;
//    private String street;
    private int wardCode;
    private int districtCode;
    private int provinceCode;
}
