package com.foodapp.foodorderingapp.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;

import jakarta.persistence.JoinColumn;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
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
//    private String ward;
//    private String district;
//    private String province;



}
